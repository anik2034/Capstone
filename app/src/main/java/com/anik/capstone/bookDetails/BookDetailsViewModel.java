package com.anik.capstone.bookDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.model.BookModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookDetailsViewModel extends ViewModel {

    private final MutableLiveData<BookModel> _bookModel = new MutableLiveData<>();
    public LiveData<BookModel> bookModel = _bookModel;

    private final MutableLiveData<Boolean> _isNewBook = new MutableLiveData<>();
    public LiveData<Boolean> isNewBook = _isNewBook;

    @Inject
    public BookDetailsViewModel() {
    }

    public void init(BookModel bookModel, Boolean isNewBook) {
        _bookModel.setValue(bookModel);
        _isNewBook.setValue(isNewBook);
    }
}
