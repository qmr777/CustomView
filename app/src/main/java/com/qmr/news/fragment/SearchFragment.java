package com.qmr.news.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.qmr.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";

    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.lv_search_result)
    ListView lv_search_result;
    @BindView(R.id.fl_root)
    FrameLayout fl_root;

    Animator showAnimator;
    Animator hideAnimator;

    private int x,y;

    public SearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(int x,int y) {
        SearchFragment fragment = new SearchFragment();
/*        Bundle bundle = new Bundle();
        bundle.putInt("X",x);
        bundle.putInt("Y",y);
        fragment.setArguments(bundle);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        x = getArguments().getInt("X");
//        y = getArguments().getInt("Y");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, v);
        x = container.getRight();

        y = container.getTop();
//        fl_root.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(showAnimator!=null && showAnimator.isRunning())
//                    showAnimator.cancel();
//
//                animateToHide();
//            }
//        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(showAnimator==null)
            animateToShow();
    }

    public void onBackPressed() {
        if (hideAnimator == null || !hideAnimator.isStarted()) {
            animateToHide();
        }
    }

    void animateToShow() {
        if (showAnimator == null) {
            showAnimator = ViewAnimationUtils.createCircularReveal(ll_main,x,y, 20, 1920);
            showAnimator.setDuration(300);
            showAnimator.setInterpolator(new LinearInterpolator());
            showAnimator.setStartDelay(100);
            showAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ll_main.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    et_search.requestFocus();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        showAnimator.start();
    }

    void animateToHide() {
        if (hideAnimator == null) {
            hideAnimator = ViewAnimationUtils.createCircularReveal(ll_main, x,y, 2000, 0);
            hideAnimator.setDuration(300);
            hideAnimator.setInterpolator(new LinearInterpolator());
            hideAnimator.setStartDelay(100);
            hideAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ll_main.setVisibility(View.GONE);
                    getActivity().getSupportFragmentManager().popBackStack();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            hideAnimator.start();
        }
    }
}
