package com.anik.capstone.db;

import com.anik.capstone.model.BookModel;

import java.util.List;

public interface DbOperationCompleteListener {
    void fetchBooks(List<BookModel> bookModels);
}
