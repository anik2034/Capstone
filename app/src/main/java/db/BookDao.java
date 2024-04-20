package db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ListType;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM books WHERE id = :id")
    BookModel getBookById(int id);

    @Query("SELECT * FROM books WHERE listType = :listType")
    List<BookModel> getBooksByListType(ListType listType);


    @Insert
    void insertBook(BookModel book);

    @Update
    void updateBook(BookModel book);

    @Delete
    void deleteBook(BookModel book);
}