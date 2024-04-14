package com.anik.capstone.network;

import com.anik.capstone.model.BookModel;

public interface BookSearchCallback {
    void onBookFound(BookModel bookModel);
    void onSearchFailed();
}