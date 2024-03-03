package com.anik.capstone.bookList.bookListViewModels;

import com.anik.capstone.bookList.BookMockData;
import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LibraryViewModel extends BookListViewModel {

    @Inject
    public LibraryViewModel(ResourceHelper resourceHelper) {
        super(resourceHelper);
    }

    @Override
    public void loadBooks() {
        super.setBooks(BookMockData.getLibraryBooks());
    }
}
