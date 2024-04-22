package db;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ListType;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookRepository {

    private final BookDao bookDao;

    public BookRepository(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<BookModel> getBooksByListType(ListType listType) {
        try {
            return bookDao.getBooksByListType(listType).get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    public BookModel getBookById(int id) {
        try {
            return bookDao.getBookById(id).get();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    public long insertBook(BookModel book) {
        try {
            return bookDao.insertBook(book).get();
        } catch (ExecutionException | InterruptedException e) {
            return -1;
        }
    }

    public int updateBook(BookModel book) {
        try {
            return bookDao.updateBook(book).get();
        } catch (ExecutionException | InterruptedException e) {
            return -1;
        }
    }

    public int deleteBook(BookModel book) {
        try {
            return bookDao.deleteBook(book).get();
        } catch (ExecutionException | InterruptedException e) {
            return -1;
        }
    }

}
