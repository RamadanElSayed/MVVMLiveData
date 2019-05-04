package com.e.mvvmlivedata.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;
public abstract class BaseFragment extends Fragment implements BaseView {

    public static final String TAG = BaseFragment.class.getSimpleName();
    private ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();
        initComponents();
        initViewModel();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    protected abstract void initViewModel();

    protected abstract void initComponents();

    @Override
    public void showErrMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
