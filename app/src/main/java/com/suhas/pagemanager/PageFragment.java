package com.suhas.pagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Suhas on 11/22/2017.
 */

public class PageFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    protected Page mPage = new Page("Hello", "Hello", "Hello", "Hello");

    public void setPage(Page page) {
        this.mPage = page;
    }

    protected View inflateFragment(int resId, LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view=inflater.inflate(resId,null);

        //set up recycle view and layout manager.
        mRecyclerView=(RecyclerView)view.findViewById(R.id.post_recycler_view);
        mLayoutManager=new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
        }


}
