package com.anik.capstone.db;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ListType;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BookDao {
    @Query("SELECT * FROM books WHERE id = :id")
    ListenableFuture<BookModel> getBookById(int id);

    @Query("SELECT * FROM books WHERE listType = :listType")
    ListenableFuture<List<BookModel>> getBooksByListType(ListType listType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<Long> insertBook(BookModel book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    ListenableFuture<Void> insertAll(List<BookModel> books);

    @Update
    ListenableFuture<Integer> updateBook(BookModel book);

    @Delete
    ListenableFuture<Void> deleteBook(BookModel book);

    @Query("DELETE FROM books")
    ListenableFuture<Void> dropTable();

}