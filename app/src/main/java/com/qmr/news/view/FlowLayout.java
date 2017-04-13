package com.qmr.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by qmr on 2017/4/13.
 *
 * @author qmr777
 */

public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static int getViewHeight(View v) {
        //return v.getMeasuredHeight() + v.getPaddingBottom() + v.getPaddingTop();
        return v.getMeasuredHeight();
    }

    static int getViewWidth(View v) {
        //return v.getMeasuredWidth() + v.getPaddingStart() + v.getPaddingEnd();
        return v.getMeasuredWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        final int childCount = getChildCount();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        final int maxWidth = width;

        int currentWidth = getPaddingStart();

        int currentTop = getPaddingTop();

        View child = getChildAt(0);
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        currentTop += lp.topMargin;

        Log.i(TAG, "onMeasure: maxWidth :" + maxWidth);

        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            lp = (MarginLayoutParams) child.getLayoutParams();
            int calcRight = (currentWidth + getViewWidth(child) + lp.leftMargin);
            if (calcRight <= maxWidth) {
                currentWidth = currentWidth + child.getMeasuredWidth() + lp.rightMargin;
                Log.i(TAG, "onMeasure不换行: " + currentWidth);
                //currentWidth = currentWidth + child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                //doNothing
            } else {//换行
                currentTop = currentTop + lp.topMargin + lp.bottomMargin + getViewHeight(child);
                currentWidth = getPaddingStart() + lp.leftMargin;
                //calcRight = (currentWidth + getViewWidth(child));//currentWidth变了 需要重新算
                //child.layout(currentWidth, currentTop, calcRight, currentTop + getViewHeight(child));
                Log.i(TAG, "onMeasure换行: " + currentWidth);
                currentWidth += child.getMeasuredWidth() + lp.rightMargin;
            }
        }//end for

        currentTop = currentTop + child.getMeasuredHeight() + lp.bottomMargin;

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? width : maxWidth,
                heightMode == MeasureSpec.EXACTLY ? height : currentTop);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout();
        int currentWidth = getPaddingStart();
        int currentTop = getPaddingTop();
        int childCount = getChildCount();
        int maxWidth = getWidthWithPadding();

        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            currentWidth += lp.leftMargin;
            //rowHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int calcRight = (currentWidth + getViewWidth(child));
            if (calcRight <= maxWidth) {
                child.layout(currentWidth, currentTop, calcRight, currentTop + getViewHeight(child));
                currentWidth += child.getMeasuredWidth() + lp.rightMargin;
                //doNothing
            } else {//需要换列
                currentTop = currentTop + lp.topMargin + lp.bottomMargin + getViewHeight(child);
                currentWidth = getPaddingStart() + lp.leftMargin;
                calcRight = (currentWidth + getViewWidth(child));//currentWidth变了 需要重新算
                child.layout(currentWidth, currentTop, calcRight, currentTop + getViewHeight(child));
                currentWidth += child.getMeasuredWidth() + lp.rightMargin;
            }
        }
    }

    private int getWidthWithPadding() {
        return getMeasuredWidth() - getPaddingRight();
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
