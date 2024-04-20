package com.anik.capstone.util;

import android.widget.ImageView;

import com.anik.capstone.R;
import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView)
                .load(imageUrl)
                .placeholder(R.drawable.thumbnail_placeholder)
                .into(imageView);
    }
}
