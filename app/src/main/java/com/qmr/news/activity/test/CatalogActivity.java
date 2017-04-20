package com.qmr.news.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.qmr.news.R;
import com.qmr.news.view.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CatalogActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_behavior)
    Button btn_behavior;
    @BindView(R.id.btn_ptr)
    Button btn_ptr;
    @BindView(R.id.btn_fl)
    Button btn_fl;
    @BindView(R.id.btn_pw)
    Button btn_pw;
    @BindView(R.id.btn_eb)
    Button btn_eb;
    @BindView(R.id.btn_ns)
    Button btn_ns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);
        btn_behavior.setOnClickListener(this);
        btn_ptr.setOnClickListener(this);
        btn_fl.setOnClickListener(this);
        btn_pw.setOnClickListener(this);
        btn_eb.setOnClickListener(this);
        btn_ns.setOnClickListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_behavior:
                startActivity(new Intent(this,BehaviorTestActivity.class));
                break;
            case R.id.btn_ptr:
                startActivity(PTRActivity.class);
                break;
            case R.id.btn_fl:
                Intent i = new Intent(this, FlowLayoutActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(i);
                break;
            case R.id.btn_pw:
                startActivity(PureWeatherActivity.class);
                break;
            case R.id.btn_eb:
                startActivity(EventbusActivity.class);
                break;
            case R.id.btn_ns:
                startActivity(NestedScrollTestActivity.class);
                break;
        }

    }

    void startActivity(Class<? extends Activity> clazz){
        startActivity(new Intent(this,clazz));
    }
}
