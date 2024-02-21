package com.anik.capstone;

import static com.anik.capstone.DisplayType.ADD;
import static com.anik.capstone.DisplayType.HOME;
import static com.anik.capstone.DisplayType.SETTINGS;
import static com.anik.capstone.DisplayType.STATISTICS;
import static com.anik.capstone.DisplayType.WISHLIST;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NavBarViewModel extends ViewModel {
    private MutableLiveData<DisplayType> display;

    @Inject
    public NavBarViewModel() {
    }

    public void init() {
        display = new MutableLiveData<>(HOME);
    }

    public MutableLiveData<DisplayType> getDisplay() {
        return display;
    }

    public void setSelectedDisplayType(int itemId) {
        if (itemId == R.id.home) {
            display.setValue(HOME);
        } else if (itemId == R.id.statistics) {
            display.setValue(STATISTICS);
        } else if (itemId == R.id.settings) {
            display.setValue(SETTINGS);
        } else if (itemId == R.id.wishlist) {
            display.setValue(WISHLIST);
        } else if (itemId == R.id.addNewBook) {
            display.setValue(ADD);
        }
    }
}


enum DisplayType {
    HOME, WISHLIST, STATISTICS, SETTINGS, ADD
}
