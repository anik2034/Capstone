package com.anik.capstone.bookDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookDetailsViewModel extends ViewModel {
    private final MutableLiveData<String> _ISBN = new MutableLiveData<>();
    public LiveData<String> ISBN = _ISBN;

    @Inject
    public BookDetailsViewModel() {
    }

    public void init(String ISBN) {
        _ISBN.setValue(ISBN);
    }
}
