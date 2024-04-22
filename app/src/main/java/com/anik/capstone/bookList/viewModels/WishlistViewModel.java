package com.anik.capstone.bookList.viewModels;

import com.anik.capstone.bookList.BookListItemCreator;
import com.anik.capstone.model.ListType;
import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import db.BookRepository;

@HiltViewModel
public class WishlistViewModel extends BookListViewModel {

    @Inject
    public WishlistViewModel(
            ResourceHelper resourceHelper,
            BookRepository bookRepository,
            BookListItemCreator bookListItemCreator) {
        super(resourceHelper, bookRepository, bookListItemCreator);
    }

    @Override
    public void loadBooks() {
        loadBookFromDatabase(ListType.WISHLIST);
    }
}
