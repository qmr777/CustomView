package com.qmr.news.utils;

import android.os.Build;
import android.view.View;

/**
 * Created by qmr on 2017/3/10.
 *
 * @author qmr777
 */

public class StatusBarUtils {

    public static final int HIDE_STATUS_BAR = View.SYSTEM_UI_FLAG_LOW_PROFILE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

    public static void hideStatusBar(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            view.setSystemUiVisibility(HIDE_STATUS_BAR);
        }
    }
}
