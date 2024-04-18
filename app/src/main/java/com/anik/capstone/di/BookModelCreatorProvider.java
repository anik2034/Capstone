package com.anik.capstone.di;

import com.anik.capstone.model.BookModelCreator;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class BookModelCreatorProvider {
    @Provides
    public BookModelCreator bookCreator() {
        return new BookModelCreator();
    }
}