package com.anik.capstone.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.UserModel;

@Database(entities = {BookModel.class, UserModel.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class BookDatabase extends RoomDatabase {

    private static BookDatabase instance;

    public abstract BookDao bookDao();
    public abstract UserDao userDao();

    public static synchronized BookDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, "book-database")
                    .build();
        }
        return instance;
    }
}