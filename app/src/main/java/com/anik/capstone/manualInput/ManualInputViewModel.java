package com.anik.capstone.manualInput;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.home.DisplayType;
import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ManualInputViewModel extends ViewModel {
    private ResourceHelper resourceHelper;

    private MutableLiveData<Boolean> _cameraStart = new MutableLiveData<>();
    public LiveData<Boolean> cameraStart = _cameraStart;

    private MutableLiveData<DisplayType> _nextScreen = new MutableLiveData<>();
    public LiveData<DisplayType> nextScreen = _nextScreen;

    @Inject
    public ManualInputViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public void init() {
        checkCameraPermissions();
    }

    public void setNextScreen(DisplayType displayType) {
        _nextScreen.setValue(displayType);
    }

    public void checkCameraPermissions() {
        if (resourceHelper.hasCameraPermission()) {
            _cameraStart.setValue(true);
        }
    }
}
