package com.qmr.news.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qmr on 2017/3/6.
 *
 * @author qmr777
 */

public class DefaultItemDecoration extends RecyclerView.ItemDecoration {

    public static final int DEFAULT_LINE_WIDTH = 2;//默认线宽2px
    public static final int DEFAULT_LINE_COLOR = Color.GRAY;

    int top;
    int bottom;
    int left;
    int right;

    private Paint mPiant;

    public DefaultItemDecoration(){
        mPiant = new Paint();
        mPiant.setColor(DEFAULT_LINE_COLOR);
        mPiant.setStrokeWidth(DEFAULT_LINE_WIDTH);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,DEFAULT_LINE_WIDTH);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = state.getItemCount();
        View child;

        for(int i = 0;i<childCount;i++){
            child = parent.getChildAt(i);
            if(child!=null){
                top = child.getTop();
                bottom = child.getBottom();
                left = child.getLeft();
                right = child.getRight();
            }

            c.drawLine(left,bottom + DEFAULT_LINE_WIDTH*0.5f,right,bottom + DEFAULT_LINE_WIDTH*0.5f,mPiant);

        }

    }
}
