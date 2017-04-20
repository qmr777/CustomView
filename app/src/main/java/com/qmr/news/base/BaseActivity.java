package com.qmr.news.base;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import com.qmr.news.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new Event1());
        EventBus.getDefault().post(new Event2());
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getev1(Event1 e1){
        Log.i(TAG, "getev1: " + e1.ev);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getev2(Event2 e1){
        Log.i(TAG, "getev1: " + e1.ev);
    }


    public static class Event1{
        int ev = 1;
    }

    public static class Event2{
        int ev = 2;
    }
}
