package com.e.mvvmlivedata.views.activities;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;

import com.e.mvvmlivedata.R;
import com.e.mvvmlivedata.base.BaseActivity;
import com.e.mvvmlivedata.base.MyApplication;
import com.e.mvvmlivedata.utils.ConnectivityReceiver;
import com.e.mvvmlivedata.views.fragments.FullScreenImageFragment;
import com.e.mvvmlivedata.views.fragments.MainFragment;
import com.e.mvvmlivedata.views.interfaces.IIssueDetail;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity implements IIssueDetail, ConnectivityReceiver.ConnectivityReceiverListener  {
    private ProgressBar mProgressBar;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout=findViewById(R.id.root);
        checkConnection();


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

    private void checkConnection() {
        String message;
        boolean isConnected = ConnectivityReceiver.isNetworkConnected(MyApplication.getInstance().getApplicationContext());
        if(isConnected)
        {
            mProgressBar = findViewById(R.id.progress_bar);

            MainFragment mainFragment = MainFragment.getInstance();
            replaceCurrentFragment(mainFragment,true);
            Snackbar.make(coordinatorLayout,"Sorry! Not connected to internet",Snackbar.LENGTH_LONG);
            showSnack(true);
        }
        else {
            showSnack(false);
        }
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById((com.google.android.material.R.id.snackbar_text));
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
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

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack(isConnected);
        Log.v("hhhhhhh",String.valueOf(isConnected));
    }
}
