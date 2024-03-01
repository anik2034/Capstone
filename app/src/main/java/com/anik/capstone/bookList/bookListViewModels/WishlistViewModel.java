package com.anik.capstone.bookList.bookListViewModels;

import com.anik.capstone.BookMockData;
import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WishlistViewModel extends BookListViewModel {

    @Inject
    public WishlistViewModel(ResourceHelper resourceHelper) {
        super(resourceHelper);
    }

    public void loadBooks() {
        super.setBooks(BookMockData.getWishlistBooks());
    }
}
