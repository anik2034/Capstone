package com.anik.capstone.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import com.anik.capstone.db.BookDao;
import com.anik.capstone.db.BookDatabase;
import com.anik.capstone.db.BookRepository;
import com.anik.capstone.db.UserDao;
import com.anik.capstone.db.UserRepository;

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

    @Singleton
    @Provides
    public UserDao provideUserDao(BookDatabase bookDatabase) {
        return bookDatabase.userDao();
    }

    @Singleton
    @Provides
    public UserRepository provideUserRepository(UserDao userDao) {
        return new UserRepository(userDao);
    }
}