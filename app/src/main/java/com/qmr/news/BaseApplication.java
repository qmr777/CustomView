package com.qmr.news;

import android.app.Application;

import com.qmr.news.model.db.DBManager;
import com.qmr.news.utils.SharePreferencesUtil;

/**
 * Created by qmr on 2017/3/11.
 *
 * @author qmr777
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferencesUtil.init(this);
        DBManager.init(this);
    }
}
