package com.example.notset;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAccessAdapter extends FragmentPagerAdapter
{

    public TabsAccessAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                LatestFragment latestFragment = new LatestFragment();
                return latestFragment;

            case 1:
                MyReportfragment myReportfragment = new MyReportfragment();
                return myReportfragment;

             default:
                 return null;
        }

    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position){
            case 0:
                return "Latest";

            case 1:
                return "My Reports";

            default:
                return null;
        }
    }
}
