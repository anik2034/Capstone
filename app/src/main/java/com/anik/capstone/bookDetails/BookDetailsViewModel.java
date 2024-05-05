package com.anik.capstone.bookDetails;

import com.anik.capstone.R;
import com.anik.capstone.db.BookRepository;
import com.anik.capstone.db.UserRepository;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.BookModelCreator;
import com.anik.capstone.model.ListType;
import com.anik.capstone.model.ReadingStatus;
import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.borrowing.BorrowingStatus;
import com.anik.capstone.model.rating.RatingModel;
import com.anik.capstone.network.RetrofitClient;
import com.anik.capstone.network.responses.BookResponse;
import com.anik.capstone.util.ResourceHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class BookDetailsViewModel extends ViewModel {

    private final MutableLiveData<List<BookDetailsItem>> _bookDetailsList = new MutableLiveData<>();
    public LiveData<List<BookDetailsItem>> bookDetailsList = _bookDetailsList;

    private final MutableLiveData<Boolean> _isProgressBarVisible = new MutableLiveData<>();
    public LiveData<Boolean> isProgressBarVisible = _isProgressBarVisible;

    private final MutableLiveData<Void> _onShowBookNotFound = new MutableLiveData<>();
    public LiveData<Void> onShowBookNotFound = _onShowBookNotFound;

    private final MutableLiveData<String> _onShowErrorMessage = new MutableLiveData<>();
    public LiveData<String> onShowErrorMessage = _onShowErrorMessage;

    private final MutableLiveData<Integer> _updateDetailItem = new MutableLiveData<>();
    public LiveData<Integer> updateDetailItem = _updateDetailItem;

    private final MutableLiveData<Void> _updateList = new MutableLiveData<>();
    public LiveData<Void> updateList = _updateList;

    private final MutableLiveData<Boolean> _isNewBook = new MutableLiveData<>();
    public LiveData<Boolean> isNewBook = _isNewBook;

    private final MutableLiveData<Void> _onShowChooseListType = new MutableLiveData<>();
    public LiveData<Void> onShowChooseListType = _onShowChooseListType;

    private final MutableLiveData<MenuItems> _updateMenuItemsVisibility = new MutableLiveData<>();
    public LiveData<MenuItems> updateMenuItemsVisibility = _updateMenuItemsVisibility;

    private final ResourceHelper resourceHelper;
    private final BookModelCreator bookModelCreator;
    private final RetrofitClient retrofitClient;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookDetailsItemCreator bookDetailsItemCreator;
    private final List<BookDetailsItem> bookDetailsItemList = new ArrayList<>();
    private BookModel bookModel;

    private boolean isEditModeActive = false;
    private List<BookDetailsItem> editedItems = new ArrayList<>();

    @Inject
    public BookDetailsViewModel(
            ResourceHelper resourceHelper,
            BookModelCreator bookModelCreator,
            RetrofitClient retrofitClient,
            BookRepository bookRepository,
            BookDetailsItemCreator bookDetailsItemCreator,
            UserRepository userRepository
    ) {
        this.resourceHelper = resourceHelper;
        this.bookModelCreator = bookModelCreator;
        this.retrofitClient = retrofitClient;
        this.bookRepository = bookRepository;
        this.bookDetailsItemCreator = bookDetailsItemCreator;
        this.userRepository = userRepository;
    }

    public void init(int bookModelId, boolean isNewBook) {
        _isNewBook.setValue(isNewBook);
        if (bookModelId >= 0) {
            bookModel = bookRepository.getBookById(bookModelId);
        } else {
            bookModel = new BookModel();
        }
        createBookDetailsList(bookModel, isNewBook);
        if (isNewBook) {
            _updateMenuItemsVisibility.setValue(new MenuItems(true, false, false));
        }
    }

    public void init(SearchType searchType, String query, boolean isNewBook) {
        _isNewBook.setValue(isNewBook);
        search(searchType, query);
    }

    private void createBookDetailsList(BookModel bookModel, boolean isNewBook) {
        this.bookModel = bookModel;
        bookDetailsItemList.addAll(bookDetailsItemCreator.create(bookModel, isNewBook));
        _bookDetailsList.setValue(bookDetailsItemList);
    }

    public void onRatingChanged(float rating, BookDetailsItem bookDetailsItem) {
        BookDetailsItem editableItem = new BookDetailsItem(bookDetailsItem);
        editableItem.setRating(rating);
        editedItems.remove(editableItem);
        editedItems.add(editableItem);
    }

    public void onTextChanged(String newText, BookDetailsItem bookDetailsItem) {
        BookDetailsItem editableItem = new BookDetailsItem(bookDetailsItem);
        editableItem.setValue(newText);
        editedItems.remove(editableItem);
        editedItems.add(editableItem);
    }

    public void onDateChanged(String date, BookDetailsItem bookDetailsItem) {
        BookDetailsItem editableItem = new BookDetailsItem(bookDetailsItem);
        editableItem.setDate(date);
        editedItems.remove(editableItem);
        editedItems.add(editableItem);
    }

    public void onOptionChanged(String selected, BookDetailsItem bookDetailsItem) {
        BookDetailsItem editableItem = new BookDetailsItem(bookDetailsItem);
        editableItem.setSelectedValue(selected);
        editedItems.remove(editableItem);
        editedItems.add(editableItem);
    }

    private void updateBookModel(BookDetailsItem item) {
        BorrowingModel borrowingModel = bookModel.getBorrowing();
        RatingModel ratingModel = bookModel.getRating();
        switch (item.getItemType()) {
            case TITLE:
                bookModel.setTitle(item.getValue());
                break;
            case AUTHOR:
                bookModel.setAuthor(item.getValue());
                break;
            case THUMBNAIL:
                bookModel.setCoverUrl(item.getThumbnailUrl());
                break;
            case GENRE:
                bookModel.setGenres(Collections.singletonList(item.getValue()));
                break;
            case BORROWING_STATUS:
                borrowingModel.setBorrowingStatus(BorrowingStatus.getBorrowingStatus(item.getSelectedValue()));
                bookModel.setBorrowing(borrowingModel);
                break;
            case BORROWED_BY:
                borrowingModel.setName(item.getValue());
                bookModel.setBorrowing(borrowingModel);
                break;
            case BORROWING_DATE:
                borrowingModel.setDate(item.getDate());
                bookModel.setBorrowing(borrowingModel);
                break;
            case READING_STATUS:
                bookModel.setReadingStatus(ReadingStatus.getReadingStatus(item.getSelectedValue()));
                break;
            case RATING_EMOTIONAL_IMPACT:
                ratingModel.setEmotionalImpact(item.getRating());
                bookModel.setRating(ratingModel);
                break;
            case RATING_CHARACTERS:
                ratingModel.setCharacter(item.getRating());
                bookModel.setRating(ratingModel);
                break;
            case RATING_PACING:
                ratingModel.setPacing(item.getRating());
                bookModel.setRating(ratingModel);
                break;
            case RATING_STORYLINE:
                ratingModel.setStoryline(item.getRating());
                bookModel.setRating(ratingModel);
                break;
            case RATING_WRITING_STYLE:
                ratingModel.setWritingStyle(item.getRating());
                bookModel.setRating(ratingModel);
                break;
            case OVERALL_RATING:
                ratingModel.setOverallRating(ratingModel.getOverallRating());
                bookModel.setRating(ratingModel);
                break;

        }
    }

    private void search(SearchType searchType, String query) {
        Call<BookResponse> call = null;
        _isProgressBarVisible.setValue(true);
        switch (searchType) {
            case ISBN:
                call = retrofitClient.bookService.searchByISBN(query);
                break;
            case TITLE:
                call = retrofitClient.bookService.searchByTitle(query);
                break;
        }
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookResponse> call, @NonNull Response<BookResponse> response) {
                _isProgressBarVisible.setValue(false);
                BookResponse bookResponse = response.body();
                if (bookResponse != null && bookResponse.getNumFound() > 0) {
                    BookModel searchedBook = bookModelCreator.convertToBook(bookResponse, userRepository.getUser());

                    createBookDetailsList(searchedBook, true);
                } else {
                    _onShowBookNotFound.setValue(null);
                }
            }


            @Override
            public void onFailure(@NonNull Call<BookResponse> call, @NonNull Throwable t) {
                _isProgressBarVisible.setValue(false);
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        _onShowErrorMessage.setValue(resourceHelper.getString(R.string.something_went_wrong));
    }

    public void onSaveClicked() {
        List<BookDetailsItem> newList = mergeBookDetails(bookDetailsItemList, editedItems);
        bookDetailsItemList.clear();
        bookDetailsItemList.addAll(newList);
        for (BookDetailsItem editableItem : editedItems) {
            updateBookModel(editableItem);
        }
        if (isEditModeActive) {
            setEditableMode(false);
            int updatedItemsCount = bookRepository.updateBook(bookModel);
            if (updatedItemsCount <= 0) {
                showErrorMessage();
            }
        } else {
            _onShowChooseListType.setValue(null);
        }
    }

    public void onSaveClicked(ListType listType) {
        bookModel.setListType(listType);
        long id = bookRepository.insertBook(bookModel);
        for (BookDetailsItem bookDetailsItem : bookDetailsItemList) {
            bookDetailsItem.setBookModelId((int) id);
            bookDetailsItem.setEditable(false);
        }
        _bookDetailsList.setValue(bookDetailsItemList);
        _isNewBook.setValue(false);
        _updateList.setValue(null);
        _updateMenuItemsVisibility.setValue(new MenuItems(false, true, true));
    }

    public void onDeleteClicked() {
        bookRepository.deleteBook(bookModel, true);
    }

    public void onEditClicked() {
        editedItems.clear();
        editedItems = new ArrayList<>();
        setEditableMode(true);
    }

    private void setEditableMode(boolean isEditModeActive) {
        for (BookDetailsItem bookDetailsItem : bookDetailsItemList) {
            bookDetailsItem.setEditable(isEditModeActive);
        }
        this.isEditModeActive = isEditModeActive;
        _bookDetailsList.setValue(bookDetailsItemList);
        _updateList.setValue(null);
        if (isEditModeActive) {
            _updateMenuItemsVisibility.setValue(new MenuItems(true, false, false));
        } else {
            _updateMenuItemsVisibility.setValue(new MenuItems(false, true, true));
        }
    }

    private List<BookDetailsItem> mergeBookDetails(
            List<BookDetailsItem> initialList,
            List<BookDetailsItem> editedList
    ) {
        Map<String, BookDetailsItem> editsMap = new HashMap<>();
        for (BookDetailsItem edit : editedList) {
            editsMap.put(edit.getId(), edit);
        }
        List<BookDetailsItem> mergedList = new ArrayList<>();

        for (int i = 0; i < initialList.size(); i++) {
            BookDetailsItem originalItem = initialList.get(i);
            if (editsMap.containsKey(originalItem.getId())) {
                mergedList.add(i, editsMap.get(originalItem.getId()));
            } else {
                mergedList.add(i, initialList.get(i));
            }
        }
        return mergedList;
    }

    public static class MenuItems {
        private final boolean isSaveVisible;
        private final boolean isDeleteVisible;
        private final boolean isEditVisible;

        public MenuItems(boolean isSaveVisible, boolean isDeleteVisible, boolean isEditVisible) {
            this.isSaveVisible = isSaveVisible;
            this.isDeleteVisible = isDeleteVisible;
            this.isEditVisible = isEditVisible;
        }

        public boolean isSaveVisible() {
            return isSaveVisible;
        }

        public boolean isDeleteVisible() {
            return isDeleteVisible;
        }

        public boolean isEditVisible() {
            return isEditVisible;
        }
    }
}

