package com.anik.capstone.bookDetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.R;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.BookModelCreator;
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

import dagger.hilt.android.lifecycle.HiltViewModel;
import db.BookRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class BookDetailsViewModel extends ViewModel {

    private final MutableLiveData<List<BookDetailsItem>> _bookDetailsList = new MutableLiveData<>();
    public LiveData<List<BookDetailsItem>> bookDetailsList = _bookDetailsList;

    private final MutableLiveData<Boolean> _isProgressBarVisible = new MutableLiveData<>();
    public LiveData<Boolean> isProgressBarVisible = _isProgressBarVisible;

    private final MutableLiveData<Boolean> _onShowBookNotFound = new MutableLiveData<>();
    public LiveData<Boolean> onShowBookNotFound = _onShowBookNotFound;

    private final MutableLiveData<String> _onShowErrorMessage = new MutableLiveData<>();
    public LiveData<String> onShowErrorMessage = _onShowErrorMessage;

    private final MutableLiveData<Integer> _updateDetailItem = new MutableLiveData<>();
    public LiveData<Integer> updateDetailItem = _updateDetailItem;

    private final List<BookDetailsItem> bookDetailsItemList = new ArrayList<>();

    private final ResourceHelper resourceHelper;
    private final BookModelCreator bookModelCreator;
    private final RetrofitClient retrofitClient;
    private final BookRepository bookRepository;
    private final BookDetailsItemCreator bookDetailsItemCreator;

    @Inject
    public BookDetailsViewModel(
            ResourceHelper resourceHelper,
            BookModelCreator bookModelCreator,
            RetrofitClient retrofitClient,
            BookRepository bookRepository,
            BookDetailsItemCreator bookDetailsItemCreator
    ) {
        this.resourceHelper = resourceHelper;
        this.bookModelCreator = bookModelCreator;
        this.retrofitClient = retrofitClient;
        this.bookRepository = bookRepository;
        this.bookDetailsItemCreator = bookDetailsItemCreator;
    }

    public void init(int id, boolean isNewBook) {
        BookModel bookModel = null;
        if (id >= 0) bookModel = bookRepository.getBookById(id);
        createBookDetailsList(bookModel, isNewBook);
    }

    public void init(String query, SearchType searchType) {
        search(query, searchType);
    }

    private void createBookDetailsList(BookModel bookModel, boolean isNewBook) {
        bookDetailsItemList.addAll(bookDetailsItemCreator.create(bookModel, isNewBook));
        _bookDetailsList.setValue(bookDetailsItemList);
    }

    public void onItemClicked(int position) {
        updateBookDetails(position, true, null, null, null,null, null);
    }

    public void onRatingChanged(float rating, int position) {
        updateBookDetails(position, false, rating, null, null,null, null);
    }

    public void onTextChanged(String oldText, String newText, int position) {
        updateBookDetails(position, false, null, oldText, newText, null, null);
    }

    public void onDateChanged(String date, int position) {
        updateBookDetails(position, false, null, null,null, date, null);
    }

    public void onOptionChanged(String selected, int position) {
        updateBookDetails(position, false, null, null,null, null, selected);
    }

    private void updateBookDetails(int position, boolean editable, Float rating, String oldText, String newText, String date, String selected) {
        if (position >= 0 && position < bookDetailsItemList.size()) {
            BookDetailsItem item = bookDetailsItemList.get(position);
            item.setEditable(editable);
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
            _bookDetailsList.setValue(bookDetailsItemList);
            _updateDetailItem.setValue(position);
            updateBook(item, oldText);
        }
    }

    private void updateBook(BookDetailsItem item, String oldText) {
        BookModel bookModel = bookRepository.getBookById(item.getBookModelId());
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
                genres.set(genres.indexOf(oldText), item.getValue());
                bookModel.setGenres(genres);
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

    private void search(String query, SearchType searchType) {
        Call<BookResponse> call = null;
        _isProgressBarVisible.setValue(true);
        switch (searchType) {
            case SEARCH_BY_ISBN:
                call = retrofitClient.bookService.searchByISBN(query);
                break;
            case SEARCH_BY_TITLE:
                call = retrofitClient.bookService.searchByTitle(query);
                break;
        }
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookResponse> call, @NonNull Response<BookResponse> response) {
                _isProgressBarVisible.setValue(false);
                BookResponse bookResponse = response.body();
                if (bookResponse != null && bookResponse.getNumFound() > 0) {
                    BookModel searchedBook = bookModelCreator.convertToBook(bookResponse);
                    long insertedItemsCount = bookRepository.insertBook(searchedBook);
                    searchedBook.setId((int)insertedItemsCount);
                    if (insertedItemsCount > 0) {
                        createBookDetailsList(searchedBook, true);
                    } else {
                        showErrorMessage();
                    }
                } else {
                    _onShowBookNotFound.setValue(true);
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
}

