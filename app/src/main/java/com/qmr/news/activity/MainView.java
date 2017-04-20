package com.qmr.news.activity;

import android.support.v4.app.Fragment;

import com.qmr.news.base.BaseView;

import java.util.List;

/**
 * Created by qmr on 2017/3/1.
 *
 * @author qmr777
 */

public interface MainView extends BaseView{

    void showSearchFragment(int x,int y);

    void hideSearchFragment();


}
