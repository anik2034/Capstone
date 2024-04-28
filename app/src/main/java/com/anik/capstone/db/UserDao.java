package com.anik.capstone.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.anik.capstone.model.UserModel;
import com.google.common.util.concurrent.ListenableFuture;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    ListenableFuture<UserModel> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<Long> insertUser(UserModel user);

    @Delete
    ListenableFuture<Integer> deleteUser(UserModel user);
}
