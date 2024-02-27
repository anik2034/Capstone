package com.anik.capstone.util;

import android.content.Context;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class ResourceHelper {

    @ApplicationContext
    Context context;

    public ResourceHelper(Context context) {
        this.context = context;
    }

    public String getString(int resId) {
        return context.getString(resId);
    }
}
