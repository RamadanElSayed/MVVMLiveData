package com.e.mvvmlivedata.base;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
    public void replaceCurrentFragment( Fragment targetFragment, boolean addToBackStack) {

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction ft = manager.beginTransaction();

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
