package com.anik.capstone.util;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.core.app.ActivityCompat;

public class ResourceHelper {
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    private static final String PREF_CAMERA_PERMISSION_REQUESTED = "camera_permission_requested";
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

    public boolean isFirstTimeCameraPermission() {
        SharedPreferences preferences = context.getSharedPreferences("my_preferences", MODE_PRIVATE);
        return preferences.getBoolean(PREF_CAMERA_PERMISSION_REQUESTED, true);
    }

    public void setFirstTimeCameraPermission(boolean isFirstTime) {
        SharedPreferences preferences = context.getSharedPreferences("my_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_CAMERA_PERMISSION_REQUESTED, isFirstTime);
        editor.apply();
    }


}
