package com.anik.capstone.addNewBook;

import androidx.lifecycle.ViewModel;

import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddNewBookViewModel extends ViewModel {

    private ResourceHelper resourceHelper;

    @Inject
    public AddNewBookViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public boolean hasCameraPermission() {
        return resourceHelper.hasCameraPermission();
    }

    public boolean isFirstTimeCameraPermission() {
        return resourceHelper.isFirstTimeCameraPermission();
    }

    public void setFirstTimeCameraPermission(boolean isFirstTime) {
        resourceHelper.setFirstTimeCameraPermission(isFirstTime);
    }

}
