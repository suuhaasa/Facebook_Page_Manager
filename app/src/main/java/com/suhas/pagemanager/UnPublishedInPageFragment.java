package com.suhas.pagemanager;

import android.content.Intent;
import android.os.Bundle;
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

public class UnPublishedInPageFragment extends PageFragment implements GraphAPIHelper.OnPostsFetchListener {

    public PageFragment newInstance(){
        return new UnPublishedInPageFragment();
    };


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return super.onCreateView(inflater, container,
                savedInstanceState);
    }
    @Override
    public void refreshFeed() {
        if(activityAssignedPage != null)
        GraphAPIHelper.fetchUnPublishedPosts(activityAssignedPage, this);
    }

    @Override
    public void onPostsFetchSuccess(List<Post> posts) {
        mAdapter = new PostRecyclerAdapter(posts);
        mAdapter.setListener(new PostRecyclerAdapter.CardClickListener(){
            public void onClick(String postId){
                Intent intent = new Intent(getActivity(), PostInsightsActivity.class);
                intent.putExtra(PostInsightsActivity.EXTRA_POSTID, postId);
                getActivity().startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setRefreshing(false);

        Log.v("hello", "num ber of unpublished posts are" + posts.size());

    }

    @Override
    public void onPostsFetchFailure(String message) {
        Log.e("UnPublishedInPageFragme", message);
    }
}


