package com.anik.capstone.util;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.core.app.ActivityCompat;

public class ResourceHelper {
    private final Context context;

    public ResourceHelper(Context context) {
        this.context = context;
    }

    public String getString(int resId) {
        return context.getString(resId);
    }

    public Drawable getDrawable(int resId) {
        return context.getDrawable(resId);
    }

    public boolean hasCameraPermission() {
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
}
