package com.anik.capstone.bookList;

import com.anik.capstone.model.BookModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BookListItemCreator {

    @Inject
    public BookListItemCreator() {}

    public List<BookListItem> convert(List<BookModel> bookModelList) {
        List<BookListItem> bookListItemList = new ArrayList<>();
        if (bookModelList != null && !bookModelList.isEmpty()) {
            for (BookModel bookModel : bookModelList) {
                bookListItemList.add(
                        new BookListItem(
                                bookModel.getId(),
                                bookModel.getTitle(),
                                bookModel.getAuthor(),
                                bookModel.getCoverUrl(),
                                bookModel.getGenres()
                        )
                );
            }
        }
        return bookListItemList;
    }
}
