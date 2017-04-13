package com.qmr.news.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by qmr on 2017/4/10.
 *
 * @author qmr777
 */

public class PTRecyclerView extends RelativeLayout implements NestedScrollingParent {

    public static final int REFRESH_DISTANCE = 200;//距离 超过多少后刷新加载
    private static final String TAG = "PTRecyclerView";
    private static final int RV_CONTENT = View.generateViewId();
    private RecyclerView rv;

    private View headerView;
    private View footerView;

    private int totalY = 0;//上拉下滑的距离，上拉为

    private Scroller scroller;

    public PTRecyclerView(Context context) {
        this(context, null);
    }

    public PTRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PTRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        rv = new RecyclerView(context);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setId(RV_CONTENT);
        addView(rv, 0, new MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public RecyclerView getRecyclerView() {
        return rv;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        rv.setAdapter(adapter);
    }

    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) rv.getLayoutManager();
    }

    public void addHeaderView(View header) {
        if (this.headerView != null)
            removeView(headerView);
        RelativeLayout.LayoutParams params
                = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ABOVE, RV_CONTENT);
        headerView = header;
        addView(header, 0, params);

    }

    public void addFooterView(View footer) {
        if (footerView != null)
            removeView(footerView);
        footerView = footer;
        RelativeLayout.LayoutParams params
                = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, RV_CONTENT);
        addView(footer, params);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return ViewCompat.SCROLL_AXIS_VERTICAL == nestedScrollAxes;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.i(TAG, "onNestedScroll: " + dyUnconsumed);
        if (dyUnconsumed < 0 && headerView != null) {//滑到底了
            totalY += dyUnconsumed;
            rv.setTop(Math.abs(totalY / 2));
            headerView.setBottom(Math.abs(totalY / 2));
        } else if(dyUnconsumed > 0 && footerView!=null){
            totalY+=dyUnconsumed;
            rv.setBottom(getBottom() - totalY/2);
            footerView.setTop(getBottom() - totalY/2);

        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
        setPosition(0);
    }

    private void setPosition(int position) {
        Log.e(TAG, "setPosition: " + totalY);
        if (totalY < 0) { //下拉刷新
            ValueAnimator va = ValueAnimator.ofInt(Math.abs(totalY), position).setDuration(Math.abs(totalY) / 2);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    totalY = -value;
                    rv.setTop(value);
                    if (headerView != null) {
                        headerView.setBottom(value);
                    }
                }
            });
            va.start();
        } else if (totalY > 0) { // 上拉加载
            ValueAnimator va = ValueAnimator.ofInt(Math.abs(totalY), position).setDuration(Math.abs(totalY)/2);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    totalY = value;
                    rv.setBottom(value);
                    if (footerView != null) {
                        footerView.setTop(value);
                    }
                }
            });
            va.start();
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

}