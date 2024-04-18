package com.anik.capstone.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
    public static final String CAPSTONE_PREFERENCES = "CAPSTONE_PREFERENCES";
    private static final String PREF_CAMERA_PERMISSION_REQUESTED = "camera_permission_requested";
    private final SharedPreferences sharedPreferences;

    public SharedPrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SharedPrefHelper.CAPSTONE_PREFERENCES, MODE_PRIVATE);
    }

    public boolean isFirstTimeCameraPermission() {
        return sharedPreferences.getBoolean(PREF_CAMERA_PERMISSION_REQUESTED, true);
    }

    public void setFirstTimeCameraPermission(boolean isFirstTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_CAMERA_PERMISSION_REQUESTED, isFirstTime);
        editor.apply();
    }
}
