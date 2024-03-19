package com.anik.capstone.addNewBook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.home.DisplayType;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BarcodeScannerViewModel extends ViewModel {


    private final MutableLiveData<Boolean> _cameraStart = new MutableLiveData<>();
    public LiveData<Boolean> cameraStart = _cameraStart;

    private final MutableLiveData<Boolean> _permissionRequest = new MutableLiveData<>();
    public LiveData<Boolean> permissionRequest = _permissionRequest;

    private final MutableLiveData<NextScreenData> _nextScreen = new MutableLiveData<>();
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

    public static class NextScreenData {
        private final DisplayType nextScreen;
        private final String data;

        public NextScreenData(DisplayType nextScreen, String data) {
            this.nextScreen = nextScreen;
            this.data = data;
        }

        public DisplayType getNextScreen() {
            return nextScreen;
        }

        public String getData() {
            return data;
        }
    }
}
