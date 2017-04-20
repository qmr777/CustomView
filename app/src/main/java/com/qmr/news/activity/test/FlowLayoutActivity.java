package com.qmr.news.activity.test;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qmr.news.R;
import com.qmr.news.activity.SplashActivity;
import com.qmr.news.model.entity.ChannelEntity;
import com.qmr.news.model.provider.ChannelLocalRepository;
import com.qmr.news.model.provider.NewsRemoteRepository;
import com.qmr.news.utils.SharePreferencesUtil;
import com.qmr.news.view.FlowLayout;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FlowLayoutActivity extends AppCompatActivity{

    private static final String TAG = "FlowLayoutActivity";

    @BindView(R.id.fl_main)
    FlowLayout flowLayout;
    @BindView(R.id.et_add)
    EditText et_add;
    @BindView(R.id.btn_add)
    Button btn_add;



    FlowLayout.Adapter<ChannelEntity> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        ButterKnife.bind(this);
        et_add.requestFocus();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData(new ChannelEntity(et_add.getText().toString(),0));
                et_add.setText(null);

            }
        });


        adapter = new FlowLayout.Adapter<ChannelEntity>() {
            @Override
            public View getView(FlowLayout parent, int position, ChannelEntity data) {
                View view = LayoutInflater.from(FlowLayoutActivity.this).inflate(R.layout.item_textview,parent,false);
                TextView tv = (TextView) view.findViewById(R.id.tv_content);
                tv.setText(data.getName());
                return view;
            }
        };
        flowLayout.setAdapter(adapter);


        Observer<List<ChannelEntity>> observer = new Observer<List<ChannelEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(List<ChannelEntity> channelEntities) {
                //ChannelEntityDao.Properties
                adapter.addData(channelEntities);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
                SharePreferencesUtil.getInstance().hasLoggedin();
            }
        };
        NewsRemoteRepository.getInstance().getChannel(observer);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged: " + newConfig.keyboard);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume: ");
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_add,0);
    }

}
