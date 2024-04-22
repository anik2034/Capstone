package com.anik.capstone.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import db.BookDao;
import db.BookDatabase;
import db.BookRepository;

@Module
@InstallIn(SingletonComponent.class)
public class BookDatabaseProvider {

    @Singleton
    @Provides
    public BookDatabase provideBookDatabase(@ApplicationContext Context context) {
        return BookDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    public BookDao provideBookDao(BookDatabase bookDatabase) {
        return bookDatabase.bookDao();
    }

    @Singleton
    @Provides
    public BookRepository provideBookRepository(BookDao bookDao) {
        return new BookRepository(bookDao);
    }
}