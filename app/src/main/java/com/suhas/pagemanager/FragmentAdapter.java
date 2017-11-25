package com.suhas.pagemanager;

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

        switch (position){
            case 0:
                // arguments
                Bundle arguments1 = new Bundle();
                PageFragment pageFragment1 = new PublishedInPageFragment();
                pageFragment1.setArguments(arguments1);
                return pageFragment1;
            case 1:
                Bundle arguments2 = new Bundle();
                PageFragment pageFragment2 = new UnPublishedInPageFragment();
                arguments2.putString("type", "true");
                pageFragment2.setArguments(arguments2);
                return pageFragment2;
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
