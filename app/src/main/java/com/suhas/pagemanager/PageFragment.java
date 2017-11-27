package com.suhas.pagemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suhas on 11/22/2017.
 */

public abstract class PageFragment extends Fragment implements FragmentCommunicator{

    protected PostRecyclerAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    public static List<Post> mainPosts;
    protected ActivityCommunicator activityCommunicator;
    protected Page activityAssignedPage;
    private static final String STRING_VALUE ="stringValue";

    public PageFragment(){

    }
    public abstract PageFragment newInstance();
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        activityCommunicator =(ActivityCommunicator)context;
        ((HomePageActivity) context).fragmentCommunicators.add(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            activityAssignedPage.setId(savedInstanceState.getString(STRING_VALUE));
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(activityAssignedPage != null)
        outState.putString(STRING_VALUE, activityAssignedPage.getId());
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null)
            activityAssignedPage.setId(savedInstanceState.getString(STRING_VALUE));

    }
    @Override
    public void onResume() {
        super.onResume();
    }
    //FragmentCommunicator interface implementation
    @Override
    public void passDataToFragment(Page page){
        activityAssignedPage = page;
        refreshFeed();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        mRecyclerView=(RecyclerView) view.findViewById(R.id.post_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<Post> post = new ArrayList<Post>();
        mAdapter = new PostRecyclerAdapter(post);
        mRecyclerView.setAdapter(mAdapter);

        refreshFeed();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeed();
            }
        });
        return view;
    }


    public abstract void refreshFeed();
}
