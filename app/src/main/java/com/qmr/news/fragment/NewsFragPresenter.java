package com.qmr.news.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qmr.news.adapter.NewsRvAdapter;
import com.qmr.news.adapter.OnItemClickListener;
import com.qmr.news.base.BasePresenter;
import com.qmr.news.model.entity.NewsEntity;
import com.qmr.news.model.provider.NewsRemoteRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by qmr on 2017/3/6.
 *
 * @author qmr777
 */

public class NewsFragPresenter implements BasePresenter {

    private static final String TAG = "NewsFragPresenter";

    private static final int DEFAULT_LOAD_NUM = 10;//

    private int currentNewsItem = 0;//已有的新闻条数

    private String channel;

    private Observer<List<NewsEntity.ResultBean.ListBean>> listObserver;

    private boolean isRefreshing = false;

    private NewsRvAdapter newsRvAdapter;

    private List<NewsEntity.ResultBean.ListBean> resultList;

    private NewsFragView newsFragView;

    public NewsFragPresenter(NewsFragView newsFragView) {
        newsRvAdapter = new NewsRvAdapter();
        newsRvAdapter.setItemClickListener(itemClickListener);
        this.newsFragView = newsFragView.init(this);
        newsFragView.attachToRecyclerView(newsRvAdapter);
    }

    public void loadMore(){
        Log.i(TAG, "loadMore");
        loadNews(channel,resultList.size());
    }

    private void loadNews(String channel,int startNum) {

        if(isRefreshing)
            return;


        listObserver = new Observer<List<NewsEntity.ResultBean.ListBean>>() {

            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                isRefreshing = true;
                Log.i(TAG, "onSubscribe: 建立订阅关系");
            }

            @Override
            public void onNext(List<NewsEntity.ResultBean.ListBean> listBeen) {
                Log.i(TAG, "onNext: " + listBeen.size());
                if(resultList == null) {
                    resultList = listBeen;
                    newsRvAdapter.setContentList(resultList);
                }
                else {
                    newsRvAdapter.insertContentList(listBeen);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                isRefreshing = false;
            }

            @Override
            public void onComplete() {
                isRefreshing = false;
            }
        };

        NewsRemoteRepository.getInstance().getNews(channel, startNum, DEFAULT_LOAD_NUM, listObserver);
    }


    @Override
    public void start() {
        loadNews("头条",0);
    }

    public void start(String channel){
        this.channel = channel;
        loadNews(channel,0);
    }

    @Override
    public void destroy() {

    }

    private final OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(RecyclerView.ViewHolder vh, int position) {
//            String url = resultList.get(position).getUrl();
//            newsFragView.startWebViewActivity(url);
            newsFragView.startNewsDetailActivity(resultList.get(position));
        }
    };
}
