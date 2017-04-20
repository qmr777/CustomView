package com.qmr.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.ListViewCompat;

import java.util.List;

/**
 * Created by qmr on 2017/3/1.
 *
 * @author qmr777
 */

public class NewsFragmentAdapter extends FragmentPagerAdapter {


    public NewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
