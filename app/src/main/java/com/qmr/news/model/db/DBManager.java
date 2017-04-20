package com.qmr.news.model.db;

import android.content.Context;

/**
 * Created by qmr on 2017/3/13.
 *
 * @author qmr777
 */

public class DBManager {

    private static final String DB_NAME = "db";

    private DaoMaster.DevOpenHelper devOpenHelper;

    private DaoMaster daoMaster;

    private DaoSession daoSession;

    private static DBManager ourInstance;

    public static DBManager getInstance() {
        return ourInstance;
    }

    private DBManager(Context context) {
        devOpenHelper = new DaoMaster.DevOpenHelper(context,DB_NAME);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

    public static void init(Context context){
        ourInstance = new DBManager(context);
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
