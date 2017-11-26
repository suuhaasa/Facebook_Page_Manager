package com.suhas.pagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suhas on 11/24/2017.
 */

public class PublishedInPageFragment extends PageFragment{

    public PageFragment newInstance(){
        return new PublishedInPageFragment();
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return super.onCreateView(inflater, container,
                savedInstanceState);
    }

    public void refreshFeed(){
        GraphAPIHelper.fetchPublishedPosts(activityAssignedPage, new GraphAPIHelper.OnPostsFetchListener(){
            @Override
            public void onPostsFetchSuccess(List<Post> posts) {
                mAdapter = new PostRecyclerAdapter(posts);
                mAdapter.setListener(new PostRecyclerAdapter.CardClickListener(){
                    public void onClick(int position){
                        Intent intent = new Intent(getActivity(), PostInsightsActivity.class);
                        intent.putExtra(PostInsightsActivity.EXTRA_POSTID, position);
                        getActivity().startActivity(intent);
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                Log.v("hello", "num ber of published posts are" + posts.size());
                mSwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onPostsFetchFailure(String message) {
                Log.e("ublishedInPageFragme", message);
            }
        });
    }
}
