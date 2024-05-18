package com.anik.capstone.bookList.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anik.capstone.bookList.BookListItem;
import com.anik.capstone.bookList.BookListItemCreator;
import com.anik.capstone.db.UserRepository;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ListType;
import com.anik.capstone.util.ResourceHelper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import com.anik.capstone.db.BookRepository;

@HiltViewModel
public class WishlistViewModel extends BookListViewModel {
    private BookModel bookModel;
    private UserRepository userRepository;
    private final MutableLiveData<Void> _onShowChooseListType = new MutableLiveData<>();
    public LiveData<Void> onShowChooseListType = _onShowChooseListType;
    @Inject
    public WishlistViewModel(
            ResourceHelper resourceHelper,
            BookRepository bookRepository,
            BookListItemCreator bookListItemCreator,
            UserRepository userRepository) {
        super(resourceHelper, bookRepository, bookListItemCreator);
        this.userRepository = userRepository;
    }
    public void onSave(ListType listType) {
        bookModel.setListType(listType);
        bookModel.setOwnerId(userRepository.getUser().getId());
        bookRepository.updateBook(bookModel);
    }

    @Override
    public void onItemClick(BookListItem bookListItem) {
        bookModel = new BookModel();
        bookModel.setId(bookListItem.getBookModelId());
        bookModel.setAuthor(bookListItem.getAuthor());
        bookModel.setTitle(bookListItem.getTitle());
        bookModel.setCoverUrl(bookListItem.getCoverUrl());
        bookModel.setGenres(bookListItem.getGenres());
        _onShowChooseListType.setValue(null);

    }

    @Override
    public void loadBooks() {
        loadBookFromDatabase(ListType.WISHLIST);
    }
}
