package com.qmr.news.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmr.news.R;
import com.qmr.news.activity.NewsActivity;
import com.qmr.news.activity.WebViewActivity;
import com.qmr.news.model.entity.NewsEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment implements NewsFragView {

    private static final String TAG = "NewsFragment";
    public static final String NEWS_BUNDLE_KEY = "news_bundle_key";

    @BindView(R.id.rv_news)
    RecyclerView rv_main;

    private LinearLayoutManager linearLayoutManager;
    private NewsFragPresenter newsFragPresenter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CHANNEL = "channel";

    // TODO: Rename and change types of parameters
    private String channel;


    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance(String channel) {
        Log.i(TAG, "newInstance");
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(CHANNEL, channel);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channel = getArguments().getString(CHANNEL);
            Log.i(TAG, "onCreate: " + channel);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news,container,false);
        ButterKnife.bind(this,v);
        newsFragPresenter = new NewsFragPresenter(this);
        newsFragPresenter.start(channel);
        initView();
        return v;
    }

    private void initView(){
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_main.setLayoutManager(linearLayoutManager);
        rv_main.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                Log.e(TAG, "onScrollStateChanged: " + newState + "  " + linearLayoutManager.findLastCompletelyVisibleItemPosition()
                + "  " + recyclerView.getAdapter().getItemCount());

                if(RecyclerView.SCROLL_STATE_IDLE == recyclerView.getScrollState() &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()
                                == recyclerView.getAdapter().getItemCount() - 1){
                    newsFragPresenter.loadMore();

                }
            }

        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void attachToRecyclerView(RecyclerView.Adapter adapter) {
        rv_main.setAdapter(adapter);
    }

    @Override
    public NewsFragView init(NewsFragPresenter p) {
        newsFragPresenter = p;
        return this;
    }

    @Override
    public void startWebViewActivity(String url) {
        Intent intent = new Intent(getActivity(),WebViewActivity.class);
        intent.putExtra(WebViewActivity.URL,url);
        super.startActivity(intent);
    }

    @Override
    public void startNewsDetailActivity(NewsEntity.ResultBean.ListBean newsContent){
        Intent intent = new Intent(getActivity(), NewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(NEWS_BUNDLE_KEY,newsContent);
        intent.putExtra(NEWS_BUNDLE_KEY,bundle);
        startActivity(intent);
    }

}
