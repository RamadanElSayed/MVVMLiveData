package com.e.mvvmlivedata.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.e.mvvmlivedata.R;

public class NicePlace {

    private String title;
    private String imageUrl;

    public NicePlace(String imageUrl, String title) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public NicePlace() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // important code for loading image here

    @BindingAdapter({ "avatar" })
    public static void loadImage(ImageView imageView, String imageUrl) {

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .circleCrop())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView);
    }
}
