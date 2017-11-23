package com.suhas.pagemanager;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Suhas on 11/22/2017.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position){
        Bundle arguments = new Bundle();
        PageFragment pageFragment = new PageFragment();
        switch (position){
            case 0:
                // arguments
                arguments.putString("type", "false");
                pageFragment.setArguments(arguments);
                return pageFragment;
            case 1:
                arguments = new Bundle();
                arguments.putString("type", "true");
                pageFragment.setArguments(arguments);
                return pageFragment;
        }
        return null;
    }
    @Override
    public int getCount(){
        return 2;
    }
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "Published Pages";
            case 1: return "Unpublished Pages";
            default: return null;
        }
    }
}
