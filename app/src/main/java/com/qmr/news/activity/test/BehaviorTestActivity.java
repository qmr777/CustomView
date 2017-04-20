package com.qmr.news.activity.test;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannedString;
import android.widget.Button;

import com.qmr.news.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Retrofit;

public class BehaviorTestActivity extends AppCompatActivity {

    NestedScrollView nestedScrollView;

    AudioManager manager;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_test);
        SpannableString string = new SpannableString("123123123");


    }
}
