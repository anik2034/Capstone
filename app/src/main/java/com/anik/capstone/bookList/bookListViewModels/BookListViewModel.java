package com.anik.capstone.bookList.bookListViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.util.ResourceHelper;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookListViewModel extends ViewModel {
    private final ResourceHelper resourceHelper;

    private final MutableLiveData<String> _title = new MutableLiveData<>();
    private final MutableLiveData<List<BookModel>> _books = new MutableLiveData<>();
    public LiveData<String> title = _title;
    public LiveData<List<BookModel>> books = _books;

    @Inject
    protected BookListViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public void init(int titleResId) {
        _title.setValue(resourceHelper.getString(titleResId));
        _books.setValue(Collections.emptyList());
    }

    protected void setBooks(List<BookModel> books) {
        _books.setValue(books);
    }

    public void loadBooks() {

    }

}
