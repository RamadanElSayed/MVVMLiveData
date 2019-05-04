package com.e.mvvmlivedata.views.activities;
import android.os.Bundle;
import com.e.mvvmlivedata.R;
import com.e.mvvmlivedata.base.BaseActivity;
import com.e.mvvmlivedata.views.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mainFragment = MainFragment.getInstance();
        replaceCurrentFragment(mainFragment,true);
    }
}
