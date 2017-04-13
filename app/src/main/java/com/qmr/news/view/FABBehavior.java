package com.qmr.news.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by qmr on 2017/3/30.
 *
 * @author qmr777
 */

public class FABBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    private static final String TAG = "FABBehavior";

    private Drawable toolbar_bg;

    private NestedScrollView dependencyView;

    private int total_y = 255;

    public FABBehavior(){
        super();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Toolbar child, View dependency) {
        super.onDependentViewChanged(parent,child,dependency);
        Log.i(TAG, "onDependentViewChanged: " + dependency.getClass().getSimpleName());
        toolbar_bg.setAlpha(255-getAlpha(dependency.getScrollY()/10));
        return false;
    }

    public FABBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {

        new Thread().start();

        Log.i(TAG, "onDependentViewChanged: " + dependency.getClass().getSimpleName());
        if(dependency instanceof NestedScrollView) {
            Log.i(TAG, "layoutDependsOn: success");
            toolbar_bg = child.getBackground();
            return true;
        }
        return false;
    }

    private static int getAlpha(int alpha){
        if(alpha < 0)
            return 0;
        if(alpha > 255)
            return 255;
        return alpha;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        toolbar_bg.setAlpha(255-getAlpha(target.getScrollY()/10));

    }
}
