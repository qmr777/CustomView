package com.qmr.news.model.provider;

import com.qmr.news.BuildConfig;
import com.qmr.news.model.entity.HeWeatherEntity;
import com.qmr.news.model.entity.SearchResultEntity;
import com.qmr.news.network.SearchNews;
import com.qmr.news.network.Weather;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
 * Created by qmr on 2017/4/14.
 *
 * @author qmr777
 */

public class WeatherRemoteRepo {

    private static final String TAG = "WeatherRemoteRepo";

    private static final String BASE_URL = "https://free-api.heweather.com/v5/";

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .build();


    public void getWeather(String city,
                          Observer<HeWeatherEntity.HeWeather5Bean> observer){
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        retrofit.create(Weather.class)
                .getWeather(city, BuildConfig.WEATHER_API_KEY)
                .map(new Function<HeWeatherEntity, HeWeatherEntity.HeWeather5Bean>() {
                    @Override
                    public HeWeatherEntity.HeWeather5Bean apply(@NonNull HeWeatherEntity heWeatherEntity) throws Exception {
                        return heWeatherEntity.getHeWeather5().get(0);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
