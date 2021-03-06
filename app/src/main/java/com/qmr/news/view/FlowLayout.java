package com.qmr.news.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmr.news.adapter.OnDataSetChanged;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by qmr on 2017/4/13.
 *
 * @author qmr777
 */

public class FlowLayout extends ViewGroup implements OnDataSetChanged {

    private static final String TAG = "FlowLayout";

    private Adapter mAdapter;

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
        return v.getMeasuredHeight();

    }

    static int getViewWidth(View v) {
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
            child.setTag(i);
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
                if(calcRight > maxWidth)
                    calcRight = maxWidth;
                child.layout(currentWidth, currentTop, calcRight, currentTop + getViewHeight(child));
                currentWidth += child.getMeasuredWidth() + lp.rightMargin;
            }
        }
    }

    private int getWidthWithPadding() {
        return getMeasuredWidth() - getPaddingRight();
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setAdapter(Adapter adapter){
        this.mAdapter = adapter;
        adapter.registerDataSetChange(this);
        setNewAdapter();
    }

    private void setNewAdapter(){
        onChanged();
    }

    @Override
    public void onChanged() {
        removeAllViews();
        if(mAdapter!=null){
            final int itemCount = mAdapter.getCount();
            for(int i = 0;i<itemCount;i++){
                View v = mAdapter.getView(this,i,mAdapter.getItem(i));
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setSelected(!v.isSelected());
                    }
                });
                addView(v);
            }
        }
    }


    public static abstract class Adapter<T>{

        private List<T> mlist = new ArrayList<>();

        private View.OnClickListener clickListener;

        private OnDataSetChanged onDataSetChanged;

        public int getCount(){
            return mlist == null?0:mlist.size();
        }

        private void registerDataSetChange(OnDataSetChanged onDataSetChanged){
            this.onDataSetChanged = onDataSetChanged;
        }

        public void setData(List<T> list){
            mlist.clear();
            mlist.addAll(list);
            if(onDataSetChanged!=null)
                onDataSetChanged.onChanged();
        }

        public void addData(List<T> list){
            mlist.addAll(list);
            if(onDataSetChanged!=null)
                onDataSetChanged.onChanged();
        }

        public void setItemClickListener(View.OnClickListener listener){
            clickListener = listener;
        }

        public void addData(T t){
            mlist.add(t);
            if(onDataSetChanged!=null)
                onDataSetChanged.onChanged();
        }

        //不需要addView
        public abstract View getView(FlowLayout parent,int position,T data);

        public T getItem(int position){
            return mlist.get(position);
        }

        public List<T> getDataList(){
            return mlist;
        }

        public void notifyDataSetChanged(){
            if(onDataSetChanged!=null)
                onDataSetChanged.onChanged();
        }

    }


}
