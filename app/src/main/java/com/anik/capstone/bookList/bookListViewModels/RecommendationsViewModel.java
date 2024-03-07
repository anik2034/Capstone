package com.anik.capstone.bookList.bookListViewModels;

import com.anik.capstone.bookList.BookMockData;
import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RecommendationsViewModel extends BookListViewModel {
    @Inject
    public RecommendationsViewModel(ResourceHelper resourceHelper) {
        super(resourceHelper);
    }

    @Override
    public void loadBooks() {
        super.setBooks(BookMockData.getRecommendationsBooks());
    }
}
