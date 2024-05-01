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
import java.util.List;

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

    private final ResourceHelper resourceHelper;
    private final BookModelCreator bookModelCreator;
    private final RetrofitClient retrofitClient;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookDetailsItemCreator bookDetailsItemCreator;
    private final List<BookDetailsItem> bookDetailsItemList = new ArrayList<>();
    private BookModel bookModel;

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

    public void onItemClicked(int position) {
        BookDetailsItem bookDetailsItem = getItemByPosition(position);
        if (bookDetailsItem != null) {
            bookDetailsItem.setEditable(true);
            updateBookDetailsItemList(position);
        }
    }

    public void onRatingChanged(float rating, int position) {
        updateBookDetails(position, rating, null, null, null, null);
    }

    public void onTextChanged(String oldText, String newText, int position) {
        updateBookDetails(position, null, oldText, newText, null, null);
    }

    public void onDateChanged(String date, int position) {
        updateBookDetails(position, null, null, null, date, null);
    }

    public void onOptionChanged(String selected, int position) {
        updateBookDetails(position, null, null, null, null, selected);
    }

    private void updateBookDetails(int position, Float rating, String oldText, String newText, String date, String selected) {
        BookDetailsItem item = getItemByPosition(position);
        if (item != null) {
            item.setEditable(false);
            if (rating != null) {
                item.setRating(rating);
            }
            if (newText != null) {
                item.setValue(newText);
            }
            if (date != null) {
                item.setDate(date);
            }
            if (selected != null) {
                item.setSelectedValue(selected);
            }
            updateBookDetailsItemList(position);
            updateBook(item, oldText);
        }
    }

    private BookDetailsItem getItemByPosition(int position) {
        if (position >= 0 && position < bookDetailsItemList.size()) {
            return bookDetailsItemList.get(position);
        }
        return null;
    }

    private void updateBookDetailsItemList(int position) {
        _bookDetailsList.setValue(bookDetailsItemList);
        _updateDetailItem.setValue(position);
    }

    private void updateBook(BookDetailsItem item, String oldText) {
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
                List<String> genres = bookModel.getGenres();
                int index = genres.indexOf(oldText);
                if (index != -1) {
                    genres.set(genres.indexOf(oldText), item.getValue());
                    bookModel.setGenres(genres);
                }
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
        int updatedItemsCount = bookRepository.updateBook(bookModel);
        if (updatedItemsCount <= 0) {
            showErrorMessage();
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
        _onShowChooseListType.setValue(null);
    }

    public void onSaveClicked(ListType listType) {
        bookModel.setListType(listType);
        long id = bookRepository.insertBook(bookModel);
        List<BookDetailsItem> list = _bookDetailsList.getValue();
        if (list != null) {
            for (BookDetailsItem bookDetailsItem : list) {
                bookDetailsItem.setBookModelId((int) id);
                bookDetailsItem.setEditable(false);
            }
        }
        _isNewBook.setValue(false);
        _updateList.setValue(null);
    }

    public void onDeleteClicked() {
        bookRepository.deleteBook(bookModel, true);
    }
}

