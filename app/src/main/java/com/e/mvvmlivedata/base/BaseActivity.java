package com.e.mvvmlivedata.base;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.e.mvvmlivedata.R;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    protected void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(getContentViewId(), fragment);
        transaction.commit();
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//    }
//
//
//    @Override
//    public void finish() {
//        super.finish();
//        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
//    }

    public void replaceCurrentFragment( Fragment targetFragment, boolean addToBackStack) {

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction ft = manager.beginTransaction();
       ft.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
      //  ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
       // ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);

        ft.replace(getContentViewId(), targetFragment, targetFragment.getClass().getName());
        if (addToBackStack) {
            ft.addToBackStack(targetFragment.getClass().getName());
        }
        ft.commit();


    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            if(getSupportActionBar() != null) {
                if(getSupportActionBar().isShowing())
                getSupportActionBar().hide();
                else
                    getSupportActionBar().show();
            }
        }
        else {
            finish();
            super.onBackPressed();
        }
    }

    protected View getRootView() {
        return this.findViewById(android.R.id.content);
    }

    protected int getContentViewId() {
        return getRootView().getId();
    }
}
