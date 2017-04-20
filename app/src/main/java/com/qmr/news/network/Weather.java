package com.qmr.news.network;

import com.qmr.news.model.entity.HeWeatherEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by qmr on 2017/4/14.
 *
 * @author qmr777
 */

public interface Weather {

    @GET("weather")
    Observable<HeWeatherEntity> getWeather(@Query("city") String city,@Query("key") String key);
}
