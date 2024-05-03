package com.anik.capstone.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.anik.capstone.R;
import com.bumptech.glide.Glide;

public class BindingAdapters {
    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageView.equals("")) {
            Glide.with(imageView)
                    .load(R.drawable.book_placeholder)
                    .into(imageView);
        } else {
            Glide.with(imageView)
                    .load(imageUrl)
                    .placeholder(R.drawable.book_placeholder)
                    .error(R.drawable.book_placeholder)
                    .into(imageView);
        }
    }
}
