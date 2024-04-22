package com.anik.capstone.newBook;

import com.anik.capstone.bookDetails.SearchType;
import com.anik.capstone.util.SingleLiveData;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BarcodeScannerViewModel extends ViewModel {
    private final MutableLiveData<Boolean> _cameraStart = new MutableLiveData<>();
    public LiveData<Boolean> cameraStart = _cameraStart;

    private final MutableLiveData<Boolean> _permissionRequest = new MutableLiveData<>();
    public LiveData<Boolean> permissionRequest = _permissionRequest;

    private final SingleLiveData<Boolean> _navigateToManualEntry = new SingleLiveData<>();
    public LiveData<Boolean> navigateToManualEntry = _navigateToManualEntry;

    private final SingleLiveData<BookDetailsData> _navigateToBookDetails = new SingleLiveData<>();
    public LiveData<BookDetailsData> navigateToBookDetails = _navigateToBookDetails;

    @Inject
    public BarcodeScannerViewModel() {}

    public void init() {
        _permissionRequest.setValue(true);
    }

    public void onReceivedPermissionResult(boolean isGranted) {
        if (isGranted) {
            _cameraStart.setValue(true);
        } else {
            navigateToManualEntry();
        }
    }

    public void onBarcodeDataReceived(String barcodeData) {
        _navigateToBookDetails.setValue(new BookDetailsData(SearchType.ISBN, barcodeData));
    }

    public void onManualInputButtonClicked() {
        navigateToManualEntry();
    }

    private void navigateToManualEntry() {
        _navigateToManualEntry.setValue(true);
    }
}
