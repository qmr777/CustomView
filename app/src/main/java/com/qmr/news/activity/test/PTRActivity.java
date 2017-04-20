package com.qmr.news.activity.test;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmr.news.R;
import com.qmr.news.adapter.NewsRvAdapter;
import com.qmr.news.model.entity.NewsEntity;
import com.qmr.news.model.provider.NewsRemoteRepository;
import com.qmr.news.view.PTRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PTRActivity extends AppCompatActivity {

    private static final String TAG = "PTRActivity";

    @BindView(R.id.ptr)
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    static final int VISIBILITY_MASK = 0x0000000C;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean isShown(View v) {
        View current = v;
        //noinspection ConstantConditions
        do {
            if ((current.getVisibility() & VISIBILITY_MASK) != View.VISIBLE) {
                return false;
            }
            ViewParent parent = current.getParent();
            if (parent == null) {
                return false; // We are not attached to the view root
            }
            if (!(parent instanceof View)) {
                return true;
            }
            current = (View) parent;
        } while (current != null);

        return false;
    }
}
