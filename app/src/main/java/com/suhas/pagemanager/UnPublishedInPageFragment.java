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

public class UnPublishedInPageFragment extends PageFragment {

    public PageFragment newInstance(){
        return new UnPublishedInPageFragment();
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflateFragment(R.layout.fragment_page, inflater, container, savedInstanceState);
        Log.v("onc reasdaf af ", "inside unpublished page");
        if(mRecyclerView == null)
            Log.i("onc reasdaf af ", "onCreateView: is null");
        return view;
    }
    @Override
    public void refreshFeed(){
        GraphAPIHelper.fetchUnPublishedPosts(activityAssignedPage, new GraphAPIHelper.OnPostsFetchListener(){

            @Override
            public void onPostsFetchSuccess(List<Post> posts) {
                List<Post> p  = new ArrayList<Post>();
                p.add(new Post("hello1", "world1", "yes1"));
                p.add(new Post("hello2", "world2", "yes2"));
                p.add(new Post("hello3", "world3", "yes3"));
                mAdapter = new PostRecyclerAdapter(p);
                mAdapter.setListener(new PostRecyclerAdapter.CardClickListener(){
                    public void onClick(int position){
                        Intent intent = new Intent(getActivity(), PostInsightsActivity.class);
                        intent.putExtra(PostInsightsActivity.EXTRA_POSTID, position);
                        getActivity().startActivity(intent);
                    }
                });

                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.invalidate();

                Log.v("hello", "num ber of unpublished posts are" + posts.size());

            }

            @Override
            public void onPostsFetchFailure(String message) {
                Log.e("UnPublishedInPageFragme", message);
            }
        });
    }
}
