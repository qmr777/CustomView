package com.qmr.news.view;

import android.support.annotation.IntDef;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by qmr on 2017/4/12.
 *
 * @author qmr777
 */

public class FlowLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "FlowLayoutManager";

    public static final int VERTICAL = OrientationHelper.VERTICAL;

    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;

    private int firstVisiblePosition;
    private int lastVisiblePosition;

    private int orientation;

    @IntDef({VERTICAL,HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    @interface Orientation{}

    public FlowLayoutManager(){
        this(VERTICAL);
    }

    public FlowLayoutManager(@Orientation int orientation){
        if(orientation == HORIZONTAL)
            throw new RuntimeException("暂时不支持横向滚动!");
        this.orientation = orientation;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(state.getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }

        int top = getPaddingTop();
        int bottom = getVertical();
        int itemCount = state.getItemCount();

        firstVisiblePosition = 0;
        lastVisiblePosition = itemCount;

        for(int i = 0;i<itemCount;i++){
            View v = getChildAt(i);
            if(v.getBottom() > top) {
                removeAndRecycleView(v, recycler);
                firstVisiblePosition++;
            } else if(v.getTop() < bottom){
                removeAndRecycleView(v,recycler);
                lastVisiblePosition-- ;
            }
        }
    }

    public int getFirstVisiblePosition() {
        return firstVisiblePosition;
    }

    public int getLastVisiblePosition() {
        return lastVisiblePosition;
    }

    @Override
    public boolean canScrollVertically() {
        return orientation == VERTICAL;
    }

    @Override
    public boolean canScrollHorizontally() {
        return orientation == HORIZONTAL;
    }

    public int getHorizontal(){
        return getWidth() - getPaddingEnd() -getPaddingStart();
    }

    public int getVertical(){
        return getHeight() - getPaddingTop() - getPaddingEnd();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
