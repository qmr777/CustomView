package com.qmr.news.network;

import com.qmr.news.model.entity.SearchResultEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by qmr on 2017/3/28.
 *
 * @author qmr777
 */

public interface SearchNews {
    //http://api.jisuapi.com/news/search?keyword=姚明&appkey=yourappkey

    @GET("search")
    Observable<SearchResultEntity>
        getSearchResult(@Query("keyword") String keyword,@Query("appkey")String appkey);
}
