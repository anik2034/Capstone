package com.anik.capstone.util;

import android.content.Context;

public class ResourceHelper {
    private final Context context;
    public ResourceHelper(Context context) {
        this.context = context;
    }

    public String getString(int resId) {
        return context.getString(resId);
    }

}
