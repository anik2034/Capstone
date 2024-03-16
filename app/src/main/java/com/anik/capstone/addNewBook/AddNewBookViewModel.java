package com.anik.capstone.addNewBook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.home.DisplayType;
import com.anik.capstone.util.ResourceHelper;
import com.anik.capstone.util.SharedPrefHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddNewBookViewModel extends ViewModel {

    private ResourceHelper resourceHelper;
    private SharedPrefHelper sharedPrefHelper;
    private MutableLiveData<Boolean> _cameraStart = new MutableLiveData<>();
    public LiveData<Boolean> cameraStart = _cameraStart;

    private MutableLiveData<Boolean> _permissionRequest = new MutableLiveData<>();
    public LiveData<Boolean> permissionRequest = _permissionRequest;

    private MutableLiveData<DisplayType> _nextScreen = new MutableLiveData<>();
    public LiveData<DisplayType> nextScreen = _nextScreen;


    @Inject
    public AddNewBookViewModel(SharedPrefHelper sharedPrefHelper, ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
        this.sharedPrefHelper = sharedPrefHelper;
    }

    public void init() {
        _permissionRequest.setValue(true);
        _cameraStart.setValue(false);
    }

    public void setNextScreen(DisplayType displayType) {
        _nextScreen.setValue(displayType);
    }

    public void checkCameraPermissions() {
        if (!sharedPrefHelper.isFirstTimeCameraPermission() && !resourceHelper.hasCameraPermission()) {
            _nextScreen.setValue(DisplayType.MANUAL_INPUT);
        } else if (resourceHelper.hasCameraPermission()) {
            _cameraStart.setValue(true);
        } else {
            sharedPrefHelper.setFirstTimeCameraPermission(false);
        }
    }
}
