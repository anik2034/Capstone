package com.anik.capstone.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitClientProvider {
    @Singleton
    @Provides
    public static RetrofitClient retrofitClient() {
        return new RetrofitClient();
    }
}