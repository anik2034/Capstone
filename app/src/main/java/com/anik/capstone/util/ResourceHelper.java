package com.anik.capstone.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ResourceHelper {
    private final Context context;
    public ResourceHelper(Context context) {
        this.context = context;
    }
    public String getString(int resId) {
        return context.getString(resId);
    }

}
