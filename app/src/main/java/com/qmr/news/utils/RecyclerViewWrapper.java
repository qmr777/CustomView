package com.qmr.news.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


/**
 * Created by qmr on 2017/2/26.
 *
 * @author qmr777
 */

public class RecyclerViewWrapper {

    private static final String TAG = "RecyclerViewWrapper";

    private RecyclerView recyclerView;

    private View mEmptyView;

    public RecyclerViewWrapper(RecyclerView recyclerView, View emptyView){
        this.recyclerView = recyclerView;
        mEmptyView = emptyView;
        //recyclerView.getAdapter().registerAdapterDataObserver(observer);
        Log.i(TAG, "RecyclerViewWrapper: init");
        checkEmpty();
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        adapter.registerAdapterDataObserver(observer);
        if(recyclerView.getAdapter()!=null)
            recyclerView.getAdapter().unregisterAdapterDataObserver(observer);

        recyclerView.setAdapter(adapter);
    }


    public void setEmptyView(@NonNull View emptyView){
        mEmptyView = emptyView;
    }

    private View getEmptyView(){
//        if(mEmptyView == null)
//            mEmptyView = View.inflate(recyclerView.getContext(),viewRes,null);
        return mEmptyView;
    }

    private void checkEmpty(){
        if(recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() == 0){
            Log.i(TAG, "checkEmpty: empty");
            recyclerView.setVisibility(View.GONE);
            getEmptyView().setVisibility(View.VISIBLE);
        } else {
            Log.e(TAG, "checkEmpty: not empty");
            getEmptyView().setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private final RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkEmpty();
        }
    };
}
