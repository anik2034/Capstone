package com.anik.capstone.bookList.viewModels;

import android.graphics.drawable.Drawable;

import com.anik.capstone.bookList.BookListItem;
import com.anik.capstone.bookList.BookListItemCreator;
import com.anik.capstone.bookList.LayoutViewType;
import com.anik.capstone.db.BookRepository;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ListType;
import com.anik.capstone.util.ResourceHelper;
import com.anik.capstone.util.SingleLiveData;

import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.anik.capstone.bookList.LayoutViewType.GRID;
import static com.anik.capstone.bookList.LayoutViewType.ROW;

public abstract class BookListViewModel extends ViewModel {
    protected final ResourceHelper resourceHelper;
    protected final BookRepository bookRepository;
    protected final BookListItemCreator bookListItemCreator;

    protected final MutableLiveData<Boolean> _isProgressBarVisible = new MutableLiveData<>();
    public LiveData<Boolean> isProgressBarVisible = _isProgressBarVisible;

    private final MutableLiveData<String> _title = new MutableLiveData<>();
    public LiveData<String> title = _title;

    private final MutableLiveData<List<BookListItem>> _books = new MutableLiveData<>();
    public LiveData<List<BookListItem>> books = _books;

    private final MutableLiveData<Drawable> _icon = new MutableLiveData<>();
    public LiveData<Drawable> icon = _icon;

    private final MutableLiveData<Boolean> _isEmptyViewVisible = new MutableLiveData<>();
    public LiveData<Boolean> isEmptyViewVisible = _isEmptyViewVisible;

    private final MutableLiveData<LayoutViewType> _layoutViewType = new MutableLiveData<>();
    public LiveData<LayoutViewType> layoutViewType = _layoutViewType;

    private final SingleLiveData<Integer> _navigateToBookDetails = new SingleLiveData<>();
    public LiveData<Integer> navigateToBookDetails = _navigateToBookDetails;

    protected BookListViewModel(
            ResourceHelper resourceHelper,
            BookRepository bookRepository,
            BookListItemCreator bookListItemCreator
    ) {
        this.resourceHelper = resourceHelper;
        this.bookRepository = bookRepository;
        this.bookListItemCreator = bookListItemCreator;
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
        if (books.isEmpty()) {
            _isEmptyViewVisible.setValue(true);
        } else {
            _isEmptyViewVisible.setValue(false);
            _books.setValue(books);
        }
    }

    abstract void loadBooks();

    public void loadBookFromDatabase(ListType listType) {
        List<BookModel> bookModelList = bookRepository.getBooksByListType(listType);
        List<BookListItem> bookListItems = bookListItemCreator.convert(bookModelList);
        setBooks(bookListItems);
    }

    public void onItemClick(BookListItem bookListItem) {
        _navigateToBookDetails.setValue(bookListItem.getBookModelId());
    }

}

