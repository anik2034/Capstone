package com.anik.capstone.manualInput;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.addNewBook.BarcodeScannerViewModel;
import com.anik.capstone.home.DisplayType;
import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ManualInputViewModel extends ViewModel {
    private final ResourceHelper resourceHelper;

    private final MutableLiveData<BarcodeScannerViewModel.NextScreenData> _nextScreen = new MutableLiveData<>();
    public LiveData<BarcodeScannerViewModel.NextScreenData> nextScreen = _nextScreen;

    private final MutableLiveData<Boolean> _onShowPermissionRequestDialog = new MutableLiveData<>();
    public LiveData<Boolean> onShowPermissionRequestDialog = _onShowPermissionRequestDialog;

    @Inject
    public ManualInputViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public void init() {
        checkCameraPermissions();
    }

    public void checkCameraPermissions() {
        if (resourceHelper.hasCameraPermission()) {
            _onShowPermissionRequestDialog.setValue(false);
        }
    }

    public void onCameraButtonClicked() {
        if (resourceHelper.hasCameraPermission()) {
            _nextScreen.setValue(new BarcodeScannerViewModel.NextScreenData(DisplayType.BARCODE_SCANNER, null));
        } else {
            _onShowPermissionRequestDialog.setValue(true);
        }
    }

    public void onSearchButtonClicked(String searchQuery) {
        _nextScreen.setValue(new BarcodeScannerViewModel.NextScreenData(DisplayType.BOOK_DETAILS, searchQuery));
    }
}
