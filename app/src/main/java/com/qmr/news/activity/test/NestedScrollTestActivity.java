package com.qmr.news.activity.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qmr.news.R;
import com.qmr.news.adapter.NewsRvAdapter;
import com.qmr.news.model.entity.NewsEntity;
import com.qmr.news.model.provider.NewsRemoteRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NestedScrollTestActivity extends AppCompatActivity {

    private static final String TAG = "NestedScrollTestAty";

    @BindView(R.id.rv_main)
    RecyclerView rv;

    List<NewsEntity.ResultBean.ListBean> resultList;
    NewsRvAdapter newsRvAdapter = new NewsRvAdapter();
    Observer<List<NewsEntity.ResultBean.ListBean>> listObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_test);
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(newsRvAdapter);

        listObserver = new Observer<List<NewsEntity.ResultBean.ListBean>>() {

            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
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
            }

            @Override
            public void onComplete() {
            }
        };

        NewsRemoteRepository.getInstance().getNews("头条", 0, 20, listObserver);
    }
}
