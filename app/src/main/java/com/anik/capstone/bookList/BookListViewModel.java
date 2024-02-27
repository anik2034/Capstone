package com.anik.capstone.bookList;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.util.ResourceHelper;
import com.anik.capstone.util.ResourceHelperProvider;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

// removed @HiltViewModel for it to be able to inject into BookListFragment field
public class BookListViewModel extends ViewModel {
    private final ResourceHelper resourceHelper;
    private MutableLiveData<String> _title = new MutableLiveData<>();
    public LiveData<String> title = _title;

    @Inject
    public BookListViewModel(@ApplicationContext Context context) {
        this.resourceHelper = ResourceHelperProvider.resourceHelper(context);
    }

    public void init(int titleResId) {
        _title.setValue(resourceHelper.getString(titleResId));
    }
}
