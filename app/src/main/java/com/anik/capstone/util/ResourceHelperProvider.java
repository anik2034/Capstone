package com.anik.capstone.util;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ResourceHelperProvider {

    @Singleton
    @Provides
    public static ResourceHelper resourceHelper(@ApplicationContext Context context) {
        return new ResourceHelper(context);
    }
}
