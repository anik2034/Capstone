package com.anik.capstone.di;

import android.content.Context;

import com.anik.capstone.db.BookDao;
import com.anik.capstone.db.BookDatabase;
import com.anik.capstone.db.BookRepository;
import com.anik.capstone.db.FirestoreDB;
import com.anik.capstone.db.UserDao;
import com.anik.capstone.db.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseProvider {

    @Singleton
    @Provides
    public BookDatabase provideBookDatabase(@ApplicationContext Context context) {
        return BookDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    public FirestoreDB provideFirestoreDB() {
        return new FirestoreDB();
    }

    @Singleton
    @Provides
    public BookDao provideBookDao(BookDatabase bookDatabase) {
        return bookDatabase.bookDao();
    }

    @Singleton
    @Provides
    public BookRepository provideBookRepository(BookDao bookDao, FirestoreDB firestoreDB) {
        return new BookRepository(bookDao, firestoreDB);
    }

    @Singleton
    @Provides
    public UserDao provideUserDao(BookDatabase bookDatabase) {
        return bookDatabase.userDao();
    }

    @Singleton
    @Provides
    public UserRepository provideUserRepository(UserDao userDao, FirestoreDB firestoreDB) {
        return new UserRepository(userDao, firestoreDB);
    }
}