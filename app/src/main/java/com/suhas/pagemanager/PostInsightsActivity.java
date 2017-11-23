package com.suhas.pagemanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PostInsightsActivity extends AppCompatActivity {

    public static final String EXTRA_POSTID = "postId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_insights);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int postId = (Integer) getIntent().getExtras().get(EXTRA_POSTID);
        String postName = Post.posts[postId].getName();
        TextView nameTextView = (TextView) findViewById(R.id.insight_post_name);
        nameTextView.setText(postName);

        String postDescription = Post.posts[postId].getName();
        TextView descriptionTextView = (TextView) findViewById(R.id.insight_post_description);
        nameTextView.setText(postDescription);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }
    @Override
    public boolean  onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }


}
