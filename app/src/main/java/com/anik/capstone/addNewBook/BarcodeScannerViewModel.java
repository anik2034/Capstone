package com.anik.capstone.addNewBook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.home.DisplayType;
import com.anik.capstone.home.NextScreenData;
import com.anik.capstone.util.SingleLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BarcodeScannerViewModel extends ViewModel {
    private final MutableLiveData<Boolean> _cameraStart = new MutableLiveData<>();
    public LiveData<Boolean> cameraStart = _cameraStart;

    private final MutableLiveData<Boolean> _permissionRequest = new MutableLiveData<>();
    public LiveData<Boolean> permissionRequest = _permissionRequest;

    private final SingleLiveData<NextScreenData> _nextScreen = new SingleLiveData<>();
    public LiveData<NextScreenData> nextScreen = _nextScreen;


    @Inject
    public BarcodeScannerViewModel() {

    }

    public void init() {
        _permissionRequest.setValue(true);
    }

    public void onReceivedPermissionResult(boolean isGranted) {
        if (isGranted) {
            _cameraStart.setValue(true);
        } else {
            _nextScreen.setValue(new NextScreenData(DisplayType.MANUAL_INPUT, null));
        }
    }

    public void onBarcodeDataReceived(String barcodeData) {
        _nextScreen.setValue(new NextScreenData(DisplayType.BOOK_DETAILS, barcodeData));
    }

    public void onManualInputButtonClicked() {
        _nextScreen.setValue(new NextScreenData(DisplayType.MANUAL_INPUT, null));
    }
}
