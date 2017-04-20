package com.qmr.news.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.qmr.news.R;
import com.qmr.news.activity.test.CatalogActivity;
import com.qmr.news.model.entity.ChannelEntity;
import com.qmr.news.model.provider.ChannelLocalRepository;
import com.qmr.news.model.provider.NewsRemoteRepository;
import com.qmr.news.utils.SharePreferencesUtil;
import com.qmr.news.utils.StatusBarUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtils.hideStatusBar(getWindow().getDecorView());

        if(SharePreferencesUtil.getInstance().isFirstLogin()){

            Observer<List<ChannelEntity>> observer = new Observer<List<ChannelEntity>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    //ChannelLocalRepository.getInstance().deleteAll();

                }

                @Override
                public void onNext(List<ChannelEntity> channelEntities) {
                    //ChannelEntityDao.Properties
                    ChannelLocalRepository.getInstance().insertAll(channelEntities);
                    Log.i(TAG, "onNext: 初始化频道完成  "+channelEntities.size());
                    for(ChannelEntity c : channelEntities){
                        Log.e(TAG, c.getName() + "  " + c.order );
                    }
                    //startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    startActivity(new Intent(SplashActivity.this, CatalogActivity.class));
                    SplashActivity.this.finish();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {
                    Log.i(TAG, "onComplete: ");
                    SharePreferencesUtil.getInstance().hasLoggedin();
                }
            };
            NewsRemoteRepository.getInstance().getChannel(observer);
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, CatalogActivity.class));
                    SplashActivity.this.finish();
                }
            }, 1);
        }
    }
}
