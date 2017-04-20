package com.qmr.news.activity.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qmr.news.R;
import com.qmr.news.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventbusActivity extends AppCompatActivity {

    @BindView(R.id.btn_ev1)
    Button btn_ev1;
    @BindView(R.id.btn_ev2)
    Button btn_ev2;
    @BindView(R.id.tv_event)
    TextView tv_event;
    private static final String TAG = "EventbusActivity";

    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }

    @OnClick({R.id.btn_ev1,R.id.btn_ev2})
    void onClick(Button btn){
        switch (btn.getId()){
            case R.id.btn_ev1:
                EventBus.getDefault().post(new Event1());
                break;
            case R.id.btn_ev2:
                EventBus.getDefault().post(new Event2());
                break;
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getev1(Event1 e1){
        Log.i(TAG, "getev1: " + e1.ev);
        tv_event.setText(Integer.toString(e1.ev));
        Toast t = new Toast(this);
        View v = getLayoutInflater().inflate(R.layout.item_textview,null);
        TextView tv = (TextView) v.findViewById(R.id.tv_content);
        tv.setText("自定义Toast");
        t.setView(v);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getev2(Event2 e1){
        Log.i(TAG, "getev1: " + e1.ev);
        tv_event.setText(Integer.toString(e1.ev));

    }


    public static class Event1{
        int ev = 1;
    }

    public static class Event2{
        int ev = 2;
    }
}
