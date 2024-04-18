package com.anik.capstone.network.responses;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class BookMakerProvider {
    @Provides
    public BookMaker bookMaker() {
        return new BookMaker();
    }
}