package com.e.mvvmlivedata.views.activities;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;

import com.e.mvvmlivedata.R;
import com.e.mvvmlivedata.base.BaseActivity;
import com.e.mvvmlivedata.views.fragments.FullScreenImageFragment;
import com.e.mvvmlivedata.views.fragments.MainFragment;
import com.e.mvvmlivedata.views.interfaces.IIssueDetail;

public class MainActivity extends BaseActivity implements IIssueDetail {
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progress_bar);

        MainFragment mainFragment = MainFragment.getInstance();
        replaceCurrentFragment(mainFragment,true);

//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                inflateFullScreenImageFragment("https://i.redd.it/qn7f9oqu7o501.jpg");
//            }
//        }, 3000);
    }
    @Override
    public void showProgressBar(){
        if(mProgressBar != null){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void hideStatusBar(){
        // Hide Status Bar
        View decorView = getWindow().getDecorView();
        // Hide Status Bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showStatusBar(){
        View decorView = getWindow().getDecorView();
        // Show Status Bar.
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void hideProgressBar(){
        if(mProgressBar != null){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideActionBar(){
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void showActionBar(){
        if(getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void inflateFullScreenImageFragment(final Object imageResource) {
        hideStatusBar();
        hideActionBar();
        FullScreenImageFragment fragment = new FullScreenImageFragment();
        fragment.setImageResource(imageResource);
        replaceCurrentFragment(fragment,true);

    }
}
