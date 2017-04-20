package com.qmr.news.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qmr on 2017/3/11.
 *
 * @author qmr777
 */

public class SharePreferencesUtil {

    private static final String TAG = "SharePreferencesUtil";

    private static SharePreferencesUtil sp;
    private static SharedPreferences sharedPreferences;

    private SharePreferencesUtil(Application application) {
        sharedPreferences = application.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public static void init(Application application) {
        sp = new SharePreferencesUtil(application);
    }

    public static SharePreferencesUtil getInstance() {
        if(sp == null)
            throw new RuntimeException(TAG + " 还没初始化");
        return sp;
    }

    public boolean isFirstLogin() {
        return sharedPreferences.getBoolean("isFirstLogin",true);
    }

    public void hasLoggedin(){
        sharedPreferences.edit().putBoolean("isFirstLogin",false).apply();
    }

}
