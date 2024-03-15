package com.anik.capstone.manualInput;

import androidx.lifecycle.ViewModel;

import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ManualInputViewModel extends ViewModel {
    private ResourceHelper resourceHelper;

    @Inject
    public ManualInputViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public boolean hasCameraPermission() {
        return resourceHelper.hasCameraPermission();
    }
}
