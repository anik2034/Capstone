package com.anik.capstone.bookList.viewModels;

import com.anik.capstone.bookList.BookListItem;
import com.anik.capstone.bookList.BookMockData;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ListType;
import com.anik.capstone.util.ResourceHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import db.BookDatabase;

@HiltViewModel
public class RecommendationsViewModel extends BookListViewModel {
    @Inject
    public RecommendationsViewModel(ResourceHelper resourceHelper, BookDatabase bookDatabase) {
        super(resourceHelper, bookDatabase);
    }

    @Override
    public void loadBooks() {
        List<BookModel> bookModelList = super.bookDao.getBooksByListType(ListType.LIBRARY);
        super.setBooks(BookListItem.convert(bookModelList));
    }
}
