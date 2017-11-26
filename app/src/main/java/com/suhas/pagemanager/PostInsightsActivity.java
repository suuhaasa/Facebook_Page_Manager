package com.suhas.pagemanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;

import org.w3c.dom.Text;

public class PostInsightsActivity extends AppCompatActivity implements GraphAPIHelper.OnPostInsightsFetchListener {
    TextView post_impressions;
    TextView post_consumptions;
    TextView post_reactions_like_total;
    public static final String EXTRA_POSTID = "postId";
    public static final String EXTRA_ACCESSTOKEN = "access_token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_insights);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String postId =  getIntent().getExtras().getString(EXTRA_POSTID);
        String access_token = getIntent().getExtras().getString(EXTRA_ACCESSTOKEN);
        /* make the API call */
        Toast.makeText(this, postId, Toast.LENGTH_LONG).show();

        GraphAPIHelper.fetchPostInsights(postId, access_token, this);

        post_impressions = (TextView) findViewById(R.id.insight_post_name);


        post_consumptions = (TextView) findViewById(R.id.insight_post_description);

        post_reactions_like_total = (TextView) findViewById(R.id.insight_post_insight3);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPostInsightsFetchSuccess(Insight insight) {
        post_impressions.setText(insight.getPost_impressions());
        post_consumptions.setText(insight.getPost_consumptions());
        post_reactions_like_total.setText(insight.getPost_reactions_like_total());
    }

    @Override
    public void onPostInsightsFetchFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
