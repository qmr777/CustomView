package com.qmr.news.model.provider;

import com.qmr.news.BuildConfig;
import com.qmr.news.model.entity.SearchResultEntity;
import com.qmr.news.network.SearchNews;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qmr on 2017/3/28.
 *
 * @author qmr777
 */

public class SearchResultRemoteRepository {

    private static final String TAG = "SearchResultRemoteRepos";

    public static final String BASE_URL = "http://api.jisuapi.com/news/";

    private static SearchResultRemoteRepository instance;

    private SearchResultRemoteRepository(){}

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .build();

    public static SearchResultRemoteRepository getInstance(){
        if(instance == null)
            instance = new SearchResultRemoteRepository();

        return instance;
    }

    public void getResult(String keyword,
                          Observer<List<SearchResultEntity.ResultBean.ListBean>> observer){
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        retrofit.create(SearchNews.class)
                .getSearchResult(keyword, BuildConfig.API_KEY)
                .map(new Function<SearchResultEntity, List<SearchResultEntity.ResultBean.ListBean>>() {
                    @Override
                    public List<SearchResultEntity.ResultBean.ListBean> apply(@NonNull SearchResultEntity searchResultEntity) throws Exception {
                        return searchResultEntity.getResult().getList();
                    }
                })
                .take(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
