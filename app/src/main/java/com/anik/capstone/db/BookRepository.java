package com.anik.capstone.db;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ListType;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class BookRepository {

    private final BookDao bookDao;
    private final FirestoreDB firestoreDB;
    private final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());

    public BookRepository(BookDao bookDao, FirestoreDB firestoreDB) {
        this.bookDao = bookDao;
        this.firestoreDB = firestoreDB;
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
            firestoreDB.addBook(book);
            return bookDao.insertBook(book).get();
        } catch (ExecutionException | InterruptedException e) {
            return -1;
        }
    }

    public void fetchAndInsertBooks(String currentUserID, Runnable onSuccess, Runnable onFailure) {
        firestoreDB.fetchBooks(currentUserID, bookModels -> {
            ListenableFuture<Void> future = bookDao.insertAll(bookModels);
            Futures.addCallback(future, new FutureCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    onSuccess.run();
                }

                @Override
                public void onFailure(@NonNull Throwable t) {
                    onFailure.run();
                }
            }, executorService);
        });
    }

    public int updateBook(BookModel book) {
        try {
            firestoreDB.updateBook(book);
            return bookDao.updateBook(book).get();
        } catch (ExecutionException | InterruptedException e) {
            return -1;
        }
    }

    public void deleteBook(BookModel book, boolean deleteFromCloud) {
        if (deleteFromCloud) firestoreDB.deleteBook(book);
        bookDao.deleteBook(book);
    }

    public void deleteAllBooks(String ownerId, boolean deleteFromCloud) {
        if (deleteFromCloud) firestoreDB.deleteAllBooks(ownerId);
        bookDao.dropTable();
    }

}
