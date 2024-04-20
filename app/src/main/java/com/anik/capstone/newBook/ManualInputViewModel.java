package com.anik.capstone.newBook;

import com.anik.capstone.R;
import com.anik.capstone.bookDetails.SearchType;
import com.anik.capstone.util.ResourceHelper;
import com.anik.capstone.util.SingleLiveData;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ManualInputViewModel extends ViewModel {

    private final ResourceHelper resourceHelper;
    private SearchType searchType = SearchType.ISBN;

    private final SingleLiveData<Boolean> _navigateToBarcodeScanner = new SingleLiveData<>();
    public LiveData<Boolean> navigateToBarcodeScanner = _navigateToBarcodeScanner;

    private final SingleLiveData<BookDetailsData> _navigateToBookDetails = new SingleLiveData<>();
    public LiveData<BookDetailsData> navigateToBookDetails = _navigateToBookDetails;

    private final MutableLiveData<Boolean> _onShowPermissionRequestDialog = new MutableLiveData<>();
    public LiveData<Boolean> onShowPermissionRequestDialog = _onShowPermissionRequestDialog;

    private final MutableLiveData<String> _showEmptySearchErrorMessage = new MutableLiveData<>();
    public LiveData<String> showEmptySearchErrorMessage = _showEmptySearchErrorMessage;

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
            _navigateToBarcodeScanner.setValue(true);
        } else {
            _onShowPermissionRequestDialog.setValue(true);
        }
    }

    public void onSearchButtonClicked(String searchQuery) {
        if (searchQuery == null || searchQuery.isEmpty()) {
            _showEmptySearchErrorMessage.setValue(resourceHelper.getString(R.string.enter_search_value));
        } else {
            _navigateToBookDetails.setValue(new BookDetailsData(searchType, searchQuery));
        }
    }

    public void onSearchTypeClicked(SearchType searchType) {
        this.searchType = searchType;
    }

}
