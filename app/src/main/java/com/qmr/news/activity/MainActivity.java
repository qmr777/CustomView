package com.qmr.news.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.qmr.news.R;
import com.qmr.news.fragment.NewsFragment;
import com.qmr.news.fragment.SearchFragment;
import com.qmr.news.model.entity.ChannelEntity;
import com.qmr.news.model.provider.ChannelLocalRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";

    private List<ChannelEntity> channelEntityList = ChannelLocalRepository.getInstance().getAll();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.tl_main)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setSupportActionBar(toolbar);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_aty,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_search:
                showSearchFragment(0,0);
                break;

            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        SearchFragment sf = (SearchFragment) getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
        if(sf!=null){
            sf.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = NewsFragment.newInstance(getPageTitle(position).toString());
            Log.i(TAG, "getItem: " + fragment.getTag() + getPageTitle(position).toString());
            return fragment;
        }

        @Override
        public int getCount() {
            return channelEntityList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return channelEntityList.get(position).getName();
        }
    };

    @Override
    public void showSearchFragment(int x,int y) {

        SearchFragment sf = (SearchFragment) getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
        Log.i(TAG, "showSearchFragment: " + (sf == null));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, sf == null?SearchFragment.newInstance(x,y):sf,SearchFragment.TAG)
                .addToBackStack(SearchFragment.TAG)
                .commit();
    }

    @Override
    public void hideSearchFragment() {

    }

}
