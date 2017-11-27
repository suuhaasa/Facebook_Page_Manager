package com.suhas.pagemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityCommunicator, CreatePostFragment.OnFragmentInteractionListener { //, PageFragment.OnListFragmentInteractionListener {

    FragmentAdapter pagerAdapter;
    ViewPager viewPager;
    List<Page> mainPages;
    public List<FragmentCommunicator> fragmentCommunicators;
    private Page currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //set up toolbar for the views.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up the floating action button for action.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreatePost();
            }
        });

        // set up drawer layout and toggle
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //set up navigation view
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        GraphAPIHelper.fetchPages(new GraphAPIHelper.OnPagesFetchListener(){
            public void onPagesFetchSuccess(List<Page> pages){
                //Toast.makeText(HomePageActivity.this, String.valueOf(pages.size()), Toast.LENGTH_SHORT).show();
                mainPages = pages;
                for(int i = 0; i < pages.size(); i++) {
                    navigationView.getMenu().add(R.id.pages_group, i, i , pages.get(i).getName());
                }
                onNavigationItemSelected(navigationView.getMenu().getItem(0));

            }
            public void onPagesFetchFailure(String message){
                Toast.makeText(HomePageActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        // set up view pager for tab layout
        viewPager = (ViewPager) findViewById(R.id.view_pager_container);
        pagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        fragmentCommunicators = new ArrayList<FragmentCommunicator>();
        // set up tab layout
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void showCreatePost() {
            FragmentManager fm = getSupportFragmentManager();
            if(currentPage != null) {
                CreatePostFragment createPostFragment = CreatePostFragment.newInstance(currentPage.getId(), currentPage.getAccess_token());
                createPostFragment.show(fm, "fragment_create_post");
            }
            else {
                Toast.makeText(this, "Page not selected", Toast.LENGTH_SHORT).show();
            }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home_page_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//
//        } else if (id == R.id.nav_gallery) {
//            Toast.makeText(this, "Gallery", Toast.LENGTH_SHORT).show();
//
//        }
        if(id == R.id.logout_item){
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this.getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.about_item){
            Toast.makeText(HomePageActivity.this, "Page Manager v0.01", Toast.LENGTH_SHORT).show();

        }
        else if(id == R.id.help_item){
            Toast.makeText(HomePageActivity.this, "For help visit www.facebook.com", Toast.LENGTH_SHORT).show();
        }
        else {
            currentPage = mainPages.get(id);
            for(FragmentCommunicator fragmentCommunicator : fragmentCommunicators)
            fragmentCommunicator.passDataToFragment(currentPage);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void passDataToActivity(String someValue){
        Toast.makeText(HomePageActivity.this, someValue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
