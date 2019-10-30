package com.e.mvvmlivedata.views.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.e.mvvmlivedata.R;
import com.e.mvvmlivedata.databinding.FragmentFullScreenProductBinding;
import com.e.mvvmlivedata.utils.ScalingImageView;
import com.e.mvvmlivedata.views.interfaces.IIssueDetail;

import java.util.Objects;

public class FullScreenImageFragment extends Fragment {

    private static final String TAG = "FullScreenImageFragment";

    //vars
    private Object mImageResource;
    private IIssueDetail mIIssueDetail;
    private boolean flag = true;

    FragmentFullScreenProductBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = this.getArguments();
//        mImageResource = bundle.getString(getString(R.string.intent_image));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFullScreenProductBinding.inflate(inflater);
        setImage();
        return binding.getRoot();
    }


    private void setImage() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        RequestOptions options = new RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)
                .override(width, height)
                .centerInside()
                .placeholder(R.drawable.ic_launcher_background);

        mIIssueDetail.showProgressBar();

        RequestListener listener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                mIIssueDetail.hideProgressBar();
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                mIIssueDetail.hideProgressBar();
                return false;
            }
        };

        Glide.with(this)
                .setDefaultRequestOptions(options)
                .load(mImageResource)
                .listener(listener)
                .into(binding.fullscreenImage);


//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (flag) {
////                    mIIssueDetail.showActionBar();
////                    flag = false;
////                } else {
////                    mIIssueDetail.hideActionBar();
////                    flag = true;
////                }
//
//            }
//        });

    }

    public void setImageResource(Object resource) {
        mImageResource = resource;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIIssueDetail = (IIssueDetail) getActivity();

    }

}