package com.anik.capstone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookListViewModel extends ViewModel {

	private MutableLiveData<String> _title = new MutableLiveData<>();
	public LiveData<String> title = _title;

	private final ResourceHelper resourceHelper;

    @Inject
    public BookListViewModel(ResourceHelper resourceHelper) {
		this.resourceHelper = resourceHelper;
    }

	public void init(int titleResId) {
		_title.setValue(resourceHelper.getString(titleResId));
	}
}
