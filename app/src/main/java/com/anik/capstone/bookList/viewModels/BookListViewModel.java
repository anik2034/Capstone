package com.anik.capstone.bookList.viewModels;

import static com.anik.capstone.bookList.LayoutViewType.GRID;
import static com.anik.capstone.bookList.LayoutViewType.ROW;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.bookList.LayoutViewType;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.util.ResourceHelper;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookListViewModel extends ViewModel {
    private final ResourceHelper resourceHelper;

    private final MutableLiveData<String> _title = new MutableLiveData<>();
    public LiveData<String> title = _title;

    private final MutableLiveData<List<BookModel>> _books = new MutableLiveData<>();
    public LiveData<List<BookModel>> books = _books;

    private final MutableLiveData<LayoutViewType> _layoutViewType = new MutableLiveData<>();
    public LiveData<LayoutViewType> layoutViewType = _layoutViewType;

    private final MutableLiveData<Drawable> _icon = new MutableLiveData<>();
    public LiveData<Drawable> icon = _icon;


    @Inject
    protected BookListViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public void init(int titleResId, LayoutViewType layoutViewType) {
        _title.setValue(resourceHelper.getString(titleResId));
        _books.setValue(Collections.emptyList());
        _layoutViewType.setValue(layoutViewType);

        loadBooks();
    }

    protected void setBooks(List<BookModel> books) {
        _books.setValue(books);
    }

    public void loadBooks() {

    }

    public void onItemViewClick() {
        if (_layoutViewType.getValue() == GRID) {
            _layoutViewType.setValue(ROW);
        } else if (_layoutViewType.getValue() == ROW) {
            _layoutViewType.setValue(GRID);
        }
    }

    public void setButtonIcon(int resId) {
        _icon.setValue(resourceHelper.getDrawable(resId));
    }
}
