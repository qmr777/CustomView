package com.qmr.news.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ScrollView;

import com.qmr.news.base.BasePresenter;
import com.qmr.news.base.BaseView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by qmr on 2017/3/1.
 *
 * @author qmr777
 */

public class MainPresenter implements BasePresenter {

    private List<Fragment> fragments;

    private MainView mainView;

    public MainPresenter(MainView mainView){
        this.mainView = mainView;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
