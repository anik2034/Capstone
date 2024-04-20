package com.anik.capstone.di;

import android.content.Context;
import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import db.BookDatabase;

@Module
@InstallIn(SingletonComponent.class)
public class BookDatabaseProvider {
    @Singleton
    @Provides
    public BookDatabase provideBookDatabase(@ApplicationContext Context context) {
        return BookDatabase.getInstance(context);
    }
}