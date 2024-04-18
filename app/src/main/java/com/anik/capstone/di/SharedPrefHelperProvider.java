package com.anik.capstone.di;

import android.content.Context;

import com.anik.capstone.util.SharedPrefHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SharedPrefHelperProvider {
    @Singleton
    @Provides
    public static SharedPrefHelper sharedPrefHelper(@ApplicationContext Context context) {
        return new SharedPrefHelper(context);
    }
}
