package com.suhas.pagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Suhas on 11/22/2017.
 */

public class PageFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PostRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    boolean type;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // handle fragment arguments
        Bundle arguments = getArguments();
        if(arguments != null)
        {
            handleArguments(arguments);
        }

        // restore saved state
        if(savedInstanceState != null)
        {
            handleSavedInstanceState(savedInstanceState);
        }

        // handle intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null)
        {
            handleExtras(extras);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        // save current instance state
        super.onSaveInstanceState(outState);

        // TODO
    }

    private void handleArguments(Bundle arguments)
    {
        this.type = Boolean.parseBoolean(arguments.getString("type"));
    }

    private void handleSavedInstanceState(Bundle savedInstanceState)
    {
        // TODO
    }

    private void handleExtras(Bundle extras)
    {
        // TODO
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page, null);

        //set up recycle view and layout manager.
        mRecyclerView = (RecyclerView) view.findViewById(R.id.post_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] publishedPostNames = new String[Post.posts.length];
        for(int i = 0; i < publishedPostNames.length; i = i + 2){
            publishedPostNames[i] = Post.posts[i].getName();
        }
        String[] publishedPostDescriptions = new String[Post.posts.length];
        for(int i = 0; i < publishedPostDescriptions.length; i = i + 2){
            publishedPostDescriptions[i] = Post.posts[i].getDescription();
        }
        String[] unpublishedPostNames = new String[Post.posts.length];
        for(int i = 1; i < unpublishedPostNames.length; i = i + 2){
            unpublishedPostNames[i] = Post.posts[i].getName();
        }
        String[] unpublishedPostDescriptions = new String[Post.posts.length];
        for(int i = 1; i < unpublishedPostDescriptions.length; i = i + 2){
            unpublishedPostDescriptions[i] = Post.posts[i].getDescription();
        }
        if(type){

            mAdapter = new PostRecyclerAdapter(publishedPostNames, publishedPostDescriptions);
            mAdapter.setListener(new PostRecyclerAdapter.CardClickListener(){
                public void onClick(int position){
                    Intent intent = new Intent(getActivity(), PostInsightsActivity.class);
                    intent.putExtra(PostInsightsActivity.EXTRA_POSTID, position);
                    getActivity().startActivity(intent);
                }
            });
        }
        else{

            mAdapter = new PostRecyclerAdapter(unpublishedPostNames, unpublishedPostDescriptions);
            mAdapter.setListener(new PostRecyclerAdapter.CardClickListener(){
                public void onClick(int position){
                    Intent intent = new Intent(getActivity(), PostInsightsActivity.class);
                    intent.putExtra(PostInsightsActivity.EXTRA_POSTID, position);
                    getActivity().startActivity(intent);
                }
            });
        }
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
