package com.anik.capstone.bookList.viewModels;

import com.anik.capstone.bookList.BookListItemCreator;
import com.anik.capstone.model.ListType;
import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import com.anik.capstone.db.BookRepository;

@HiltViewModel
public class RecommendationsViewModel extends BookListViewModel {

    @Inject
    public RecommendationsViewModel(
            ResourceHelper resourceHelper,
            BookRepository bookRepository,
            BookListItemCreator bookListItemCreator) {
        super(resourceHelper, bookRepository, bookListItemCreator);
    }

    @Override
    public void loadBooks() {
        loadBookFromDatabase(ListType.LIBRARY);
    }
}
