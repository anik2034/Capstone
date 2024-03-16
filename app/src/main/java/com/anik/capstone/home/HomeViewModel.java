package com.anik.capstone.home;


import static com.anik.capstone.home.DisplayType.ADD_NEW_BOOK;
import static com.anik.capstone.home.DisplayType.BOOK_WANTS;
import static com.anik.capstone.home.DisplayType.HOME;
import static com.anik.capstone.home.DisplayType.SETTINGS;
import static com.anik.capstone.home.DisplayType.STATISTICS;
import static com.anik.capstone.home.DisplayType.WISHLIST;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.R;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final MutableLiveData<DisplayType> _displayType = new MutableLiveData<>(HOME);
    LiveData<DisplayType> displayType = _displayType;

    @Inject
    public HomeViewModel() {
    }

    public void init() {
        _displayType.setValue(HOME);
    }

    public void onDisplayTypeChange(int itemId) {
        if (itemId == R.id.home) {
            _displayType.setValue(HOME);
        } else if (itemId == R.id.statistics) {
            _displayType.setValue(STATISTICS);
        } else if (itemId == R.id.settings) {
            _displayType.setValue(SETTINGS);
        } else if (itemId == R.id.bookWants) {
            _displayType.setValue(BOOK_WANTS);
        } else if (itemId == R.id.addNewBook) {
            _displayType.setValue(ADD_NEW_BOOK);
        }
    }
}


