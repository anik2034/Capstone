package com.anik.capstone.bookList.viewModels;

import static com.anik.capstone.bookList.LayoutViewType.GRID;
import static com.anik.capstone.bookList.LayoutViewType.ROW;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.bookList.BookListItem;
import com.anik.capstone.bookList.LayoutViewType;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.util.ResourceHelper;
import com.anik.capstone.util.SingleLiveData;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import db.BookDao;
import db.BookDatabase;

@HiltViewModel
public class BookListViewModel extends ViewModel {
    private final ResourceHelper resourceHelper;

    private final MutableLiveData<String> _title = new MutableLiveData<>();
    public LiveData<String> title = _title;

    private final MutableLiveData<List<BookListItem>> _books = new MutableLiveData<>();
    public LiveData<List<BookListItem>> books = _books;

    private final MutableLiveData<Drawable> _icon = new MutableLiveData<>();
    public LiveData<Drawable> icon = _icon;

    private final MutableLiveData<LayoutViewType> _layoutViewType = new MutableLiveData<>();
    public LiveData<LayoutViewType> layoutViewType = _layoutViewType;

    private SingleLiveData<NavigateData> _onNavigate = new SingleLiveData<>();
    public LiveData<NavigateData> onNavigate = _onNavigate;

    protected BookDao bookDao;


    @Inject
    protected BookListViewModel(ResourceHelper resourceHelper, BookDatabase bookDatabase) {
        this.resourceHelper = resourceHelper;
        bookDao = bookDatabase.bookDao();
    }

    public void init(int titleResId, LayoutViewType layoutViewType) {
        _title.setValue(resourceHelper.getString(titleResId));
        _books.setValue(Collections.emptyList());
        _layoutViewType.setValue(layoutViewType);

        loadBooks();
    }

    public void setButtonIcon(int resId) {
        _icon.setValue(resourceHelper.getDrawable(resId));
    }

    public void onItemViewClick() {
        if (_layoutViewType.getValue() == GRID) _layoutViewType.setValue(ROW);
        else if (_layoutViewType.getValue() == ROW) _layoutViewType.setValue(GRID);
    }

    protected void setBooks(List<BookListItem> books) {
        _books.setValue(books);
    }

    public void loadBooks() {

    }

    public void onItemClick(BookListItem bookListItem) {
        _onNavigate.setValue(new NavigateData(bookListItem.getId(), false));
    }

    public class NavigateData {
        public final int id;
        public final boolean isNewBook;

        public NavigateData(int id, boolean isNewBook) {
            this.id = id;
            this.isNewBook = isNewBook;
        }
    }
}

