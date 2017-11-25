package com.suhas.pagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Suhas on 11/24/2017.
 */

public class UnPublishedInPageFragment extends PageFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflateFragment(R.layout.fragment_page, inflater, container, savedInstanceState);

        refreshPage();

        return view;
    }
    public void refreshPage(){
        GraphAPIHelper.fetchUnPublishedPosts(mPage.getId(), new GraphAPIHelper.OnPostsFetchListener(){

            @Override
            public void onPostsFetchSuccess(List<Post> posts) {
                PostRecyclerAdapter mAdapter = new PostRecyclerAdapter(posts);
                mAdapter.setListener(new PostRecyclerAdapter.CardClickListener(){
                    public void onClick(int position){
                        Intent intent = new Intent(getActivity(), PostInsightsActivity.class);
                        intent.putExtra(PostInsightsActivity.EXTRA_POSTID, position);
                        getActivity().startActivity(intent);
                    }
                });
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onPostsFetchFailure(String message) {
                Log.e("UnPublishedInPageFragme", message);
            }
        });
    }
}
