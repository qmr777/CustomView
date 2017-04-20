package com.qmr.news.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.qmr.news.base.BaseView;
import com.qmr.news.model.entity.NewsEntity;

/**
 * Created by qmr on 2017/3/6.
 *
 * @author qmr777
 */

public interface NewsFragView extends BaseView {

    void attachToRecyclerView(RecyclerView.Adapter adapter);

    NewsFragView init(NewsFragPresenter p);

    void startWebViewActivity(String url);

    void startNewsDetailActivity(NewsEntity.ResultBean.ListBean newsContent);


}
