package com.qmr.news.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qmr.news.R;
import com.qmr.news.base.BaseActivity;
import com.qmr.news.model.entity.ChannelEntity;
import com.qmr.news.model.entity.NewsEntity;
import com.qmr.news.model.provider.ChannelLocalRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qmr.news.fragment.NewsFragment.NEWS_BUNDLE_KEY;

public class NewsActivity extends BaseActivity {

    private NewsEntity.ResultBean.ListBean newsContent;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_collapsing_toolbar)
    ImageView iv_collapsing_toolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wv_content)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        newsContent = getIntent().getBundleExtra(NEWS_BUNDLE_KEY).getParcelable(NEWS_BUNDLE_KEY);
        initView();
    }

    @SuppressWarnings("ConstantConditions")
    void initView(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(),"openImg");

        collapsingToolbarLayout.setTitle(newsContent.getTitle());
        Glide.with(this)
                .load(newsContent.getPic())
                .centerCrop()
                .into(iv_collapsing_toolbar);

        String html = newsContent.getContent();
        //webView.loadData(html,"text/html","UTF-8");
        webView.loadDataWithBaseURL(null,html,"text/html","UTF-8",null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private final class JavaScriptInterface{

        @android.webkit.JavascriptInterface
        public void openImg(String imgSrc){

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
