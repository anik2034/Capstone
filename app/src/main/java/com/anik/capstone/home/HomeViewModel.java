package com.anik.capstone.home;


import static com.anik.capstone.home.DisplayType.BARCODE_SCANNER;
import static com.anik.capstone.home.DisplayType.HOME;
import static com.anik.capstone.home.DisplayType.MANUAL_INPUT;
import static com.anik.capstone.home.DisplayType.RECOMMENDATIONS;
import static com.anik.capstone.home.DisplayType.SETTINGS;
import static com.anik.capstone.home.DisplayType.WISHLIST;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.R;
import com.anik.capstone.util.ResourceHelper;
import com.anik.capstone.util.SharedPrefHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final MutableLiveData<DisplayType> _displayType = new MutableLiveData<>(HOME);
    LiveData<DisplayType> displayType = _displayType;
    private final SharedPrefHelper sharedPrefHelper;
    private final ResourceHelper resourceHelper;

    @Inject
    public HomeViewModel(SharedPrefHelper sharedPrefHelper, ResourceHelper resourceHelper) {
        this.sharedPrefHelper = sharedPrefHelper;
        this.resourceHelper = resourceHelper;
    }

    public void init() {
        _displayType.setValue(HOME);
    }

    public void onDisplayTypeChange(int itemId) {
        if (itemId == R.id.home) {
            _displayType.setValue(HOME);
        } else if (itemId == R.id.recs) {
            _displayType.setValue(RECOMMENDATIONS);
        } else if (itemId == R.id.settings) {
            _displayType.setValue(SETTINGS);
        } else if (itemId == R.id.wishlist) {
            _displayType.setValue(WISHLIST);
        } else if (itemId == R.id.addNewBook) {
            if (sharedPrefHelper.isFirstTimeCameraPermission()) {
                _displayType.setValue(BARCODE_SCANNER);
                sharedPrefHelper.setFirstTimeCameraPermission(false);
            } else if (resourceHelper.hasCameraPermission()) {
                _displayType.setValue(BARCODE_SCANNER);
            } else {
                _displayType.setValue(MANUAL_INPUT);
            }
        }
    }
}


