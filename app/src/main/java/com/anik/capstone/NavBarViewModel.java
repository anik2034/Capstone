package com.anik.capstone;

import static com.anik.capstone.DisplayType.ADD;
import static com.anik.capstone.DisplayType.HOME;
import static com.anik.capstone.DisplayType.SETTINGS;
import static com.anik.capstone.DisplayType.STATISTICS;
import static com.anik.capstone.DisplayType.WISHLIST;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NavBarViewModel extends ViewModel {

    private MutableLiveData<DisplayType> _displayType = new MutableLiveData<>(HOME);
	LiveData<DisplayType> displayType = _displayType;

    @Inject
    public NavBarViewModel() {
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
        } else if (itemId == R.id.wishlist) {
	        _displayType.setValue(WISHLIST);
        } else if (itemId == R.id.addNewBook) {
	        _displayType.setValue(ADD);
        }
    }
}


enum DisplayType {
    HOME,
	WISHLIST,
	STATISTICS,
	SETTINGS,
	ADD
}
