package com.qmr.news.activity.test;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.qmr.news.R;
import com.qmr.news.adapter.NewsRvAdapter;
import com.qmr.news.model.entity.NewsEntity;
import com.qmr.news.model.provider.NewsRemoteRepository;
import com.qmr.news.view.PTRV;
import com.qmr.news.view.PTRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sw.angel.recyclerlayout.layout.SWPullRecyclerLayout;

public class PTRActivity extends AppCompatActivity {

    private static final String TAG = "PTRActivity";

    @BindView(R.id.ptr)
//    SWPullRecyclerLayout ptr;
            PTRecyclerView ptr;

    private RecyclerView rv;

    private NewsRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr);
        ButterKnife.bind(this);
        rv = ptr.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(this));
        //ptr.setMyRecyclerView(new LinearLayoutManager(this),adapter = new NewsRvAdapter());
        rv.setAdapter(adapter = new NewsRvAdapter());
        NewsRemoteRepository.getInstance()
                .getNews("头条", 0, 10, new Observer<List<NewsEntity.ResultBean.ListBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<NewsEntity.ResultBean.ListBean> listBeen) {
                adapter.setContentList(listBeen);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        TextView tv = new TextView(this);
        tv.setText("header\nheader\nheader\nheader\nheader\nheader\nheader\nheader\nheader\nheader\n");
        ptr.addHeaderView(tv);

        TextView ftv = new TextView(this);
        ftv.setText("footer\nfooter\nfooter\nfooter\nfooter\nfooter\nfooter\nfooter\nfooter\nfooter\n");
        ptr.addFooterView(ftv);

    }

}
