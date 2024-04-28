package com.anik.capstone.db;

import com.anik.capstone.model.UserModel;
import com.google.common.util.concurrent.ListenableFuture;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    ListenableFuture<UserModel> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<Void> insertUser(UserModel user);

    @Delete
    ListenableFuture<Void> deleteUser(UserModel user);

    @Query("DELETE FROM users")
    ListenableFuture<Void> dropTable();
}
