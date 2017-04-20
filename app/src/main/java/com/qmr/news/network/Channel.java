package com.qmr.news.network;

import com.qmr.news.model.entity.ChannelEntityy;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by qmr on 2017/3/13.
 *
 * @author qmr777
 */


public interface Channel {
    @GET("channel")
    Observable<ChannelEntityy> getChannel(@Query("appkey") String appkey);

}
