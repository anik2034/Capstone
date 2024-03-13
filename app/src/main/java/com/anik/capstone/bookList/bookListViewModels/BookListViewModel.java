package com.anik.capstone.bookList.bookListViewModels;

import static com.anik.capstone.bookList.ItemViewType.GRID;
import static com.anik.capstone.bookList.ItemViewType.ROW;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.bookList.ItemViewType;
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
    private final MutableLiveData<List<BookModel>> _books = new MutableLiveData<>();
    private final MutableLiveData<ItemViewType> _itemViewType = new MutableLiveData<>();
    private final MutableLiveData<Drawable> _icon = new MutableLiveData<>();
    public LiveData<String> title = _title;
    public LiveData<List<BookModel>> books = _books;
    public LiveData<ItemViewType> itemViewType = _itemViewType;
    public LiveData<Drawable> icon = _icon;

    @Inject
    protected BookListViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public void setButtonIcon(int resId) {
        _icon.setValue(resourceHelper.getDrawable(resId));
    }

    public void onItemViewClick() {
        if (_itemViewType.getValue() == GRID) {
            _itemViewType.setValue(ROW);
        } else if (_itemViewType.getValue() == ROW) {
            _itemViewType.setValue(GRID);
        }
    }

    public void init(int titleResId) {
        _title.setValue(resourceHelper.getString(titleResId));
        _books.setValue(Collections.emptyList());
        _itemViewType.setValue(ItemViewType.GRID);
    }

    protected void setBooks(List<BookModel> books) {
        _books.setValue(books);
    }

    public void loadBooks() {

    }
}
