package com.anik.capstone.bookDetails;


import android.view.View;

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
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class BookDetailsViewModel extends ViewModel {

    private final MutableLiveData<List<BookDetailsItem>> _bookDetailsList = new MutableLiveData<>();
    public LiveData<List<BookDetailsItem>> bookDetailsList = _bookDetailsList;

    private final MutableLiveData<Integer> _progressBarVisibility = new MutableLiveData<>();
    public LiveData<Integer> progressBarVisibility = _progressBarVisibility;

    private final MutableLiveData<Boolean> _onShowBookNotFound = new MutableLiveData<>();
    public LiveData<Boolean> onShowBookNotFound = _onShowBookNotFound;

    private final MutableLiveData<Integer> _updateDetailItem = new MutableLiveData<>();
    public LiveData<Integer> updateDetailItem = _updateDetailItem;

    private final ResourceHelper resourceHelper;
    private final List<BookDetailsItem> bookDetailsItemList = new ArrayList<>();
    private final BookModelCreator bookModelCreator;
    private final RetrofitClient retrofitClient;

    @Inject
    public BookDetailsViewModel(ResourceHelper resourceHelper, BookModelCreator bookModelCreator, RetrofitClient retrofitClient) {
        this.resourceHelper = resourceHelper;
        this.bookModelCreator = bookModelCreator;
        this.retrofitClient = retrofitClient;
    }

    public void init(BookModel bookModel, boolean isNewBook) {
        createBookDetailsList(bookModel, isNewBook);
    }

    public void init(String query, SearchType searchType) {
        search(query, searchType);
    }

    private void createBookDetailsList(BookModel bookModel, boolean isNewBook) {
        BorrowingModel borrowingModel = new BorrowingModel(BorrowingStatus.NOT_BORROWED, "", "");
        ReadingStatus readingStatus = ReadingStatus.NOT_STARTED;
        RatingModel ratingModel = new RatingModel();
        String url = "sample";
        String title = "Title";
        String author = "Author";
        List<String> genres = Arrays.asList("Genre 1", "Genre 2");
        if (bookModel != null) {
            if (bookModel.getTitle() != null) {
                title = bookModel.getTitle();
            }
            if (bookModel.getAuthor() != null) {
                author = bookModel.getAuthor();
            }
            if (bookModel.getCoverUrl() != null) {
                url = bookModel.getCoverUrl();
            }
            if (bookModel.getGenres() != null) {
                genres = bookModel.getGenres();
            }
            if (bookModel.getReadingStatus() != null) {
                readingStatus = bookModel.getReadingStatus();
            }
            if (bookModel.getBorrowing() != null) {
                borrowingModel = bookModel.getBorrowing();
                if (borrowingModel.getBorrowingStatus() == null)
                    borrowingModel.setBorrowingStatus(BorrowingStatus.NOT_BORROWED);
                if (borrowingModel.getDate() == null) borrowingModel.setDate("");
                if (borrowingModel.getName() == null)
                    borrowingModel.setName("");
            }
            if (bookModel.getRating() != null) {
                ratingModel = bookModel.getRating();
            }
        }
        BookDetailsItem valueTitle = new BookDetailsItem();
        setEditableText(valueTitle, true, title, isNewBook);
        bookDetailsItemList.add(valueTitle);

        BookDetailsItem valueAuthor = new BookDetailsItem();
        setEditableText(valueAuthor, true, author, isNewBook);
        bookDetailsItemList.add(valueAuthor);

        BookDetailsItem valueUrl = new BookDetailsItem();
        valueUrl.setThumbnailUrl(url);
        valueUrl.setItemViewType(BookDetailsItem.ItemViewType.THUMBNAIL);
        bookDetailsItemList.add(valueUrl);

        for (String genre : genres) {
            BookDetailsItem headerGenre = new BookDetailsItem();
            BookDetailsItem valueGenre = new BookDetailsItem();
            setHeader(headerGenre, resourceHelper.getString(R.string.genre));
            setEditableText(valueGenre, false, genre, isNewBook);
            bookDetailsItemList.add(headerGenre);
            bookDetailsItemList.add(valueGenre);
        }

        BookDetailsItem headerReadingStatus = new BookDetailsItem();
        BookDetailsItem valueReadingStatus = new BookDetailsItem();
        setHeader(headerReadingStatus, resourceHelper.getString(R.string.reading_status));
        setSelectedValue(valueReadingStatus, ReadingStatus.getAllDisplayNames(), readingStatus.getDisplayName(), isNewBook);
        bookDetailsItemList.add(headerReadingStatus);
        bookDetailsItemList.add(valueReadingStatus);

        BookDetailsItem headerBorrowingStatus = new BookDetailsItem();
        BookDetailsItem valueBorrowingStatus = new BookDetailsItem();
        setHeader(headerBorrowingStatus, resourceHelper.getString(R.string.borrowing_status));
        setSelectedValue(valueBorrowingStatus, BorrowingStatus.getAllDisplayNames(), borrowingModel.getBorrowingStatus().getDisplayName(), isNewBook);
        bookDetailsItemList.add(headerBorrowingStatus);
        bookDetailsItemList.add(valueBorrowingStatus);

        BookDetailsItem headerBorrowingDetails = new BookDetailsItem();
        setHeader(headerBorrowingDetails, "Borrowing Details");

        BookDetailsItem valueBorrowingName = new BookDetailsItem();
        setEditableText(valueBorrowingName, false, borrowingModel.getName(), isNewBook);
        bookDetailsItemList.add(headerBorrowingDetails);
        bookDetailsItemList.add(valueBorrowingName);

        BookDetailsItem valueBorrowingDate = new BookDetailsItem();
        valueBorrowingDate.setItemViewType(BookDetailsItem.ItemViewType.DATE);
        valueBorrowingDate.setDate(borrowingModel.getDate());
        valueBorrowingDate.setEditable(isNewBook);
        bookDetailsItemList.add(valueBorrowingDate);

        BookDetailsItem headerRating = new BookDetailsItem();
        setHeader(headerRating, resourceHelper.getString(R.string.rating));
        bookDetailsItemList.add(headerRating);

        BookDetailsItem valueEmotionalImpact = new BookDetailsItem();
        setStarRating(valueEmotionalImpact, resourceHelper.getString(R.string.emotional_impact), ratingModel.getEmotionalImpact(), isNewBook);
        bookDetailsItemList.add(valueEmotionalImpact);

        BookDetailsItem valueCharacter = new BookDetailsItem();
        setStarRating(valueCharacter, resourceHelper.getString(R.string.characters), ratingModel.getCharacter(), isNewBook);
        bookDetailsItemList.add(valueCharacter);

        BookDetailsItem valuePacing = new BookDetailsItem();
        setStarRating(valuePacing, resourceHelper.getString(R.string.pacing), ratingModel.getPacing(), isNewBook);
        bookDetailsItemList.add(valuePacing);

        BookDetailsItem valueStoryLine = new BookDetailsItem();
        setStarRating(valueStoryLine, resourceHelper.getString(R.string.story_line), ratingModel.getStoryline(), isNewBook);
        bookDetailsItemList.add(valueStoryLine);

        BookDetailsItem valueWritingStyle = new BookDetailsItem();
        setStarRating(valueWritingStyle, resourceHelper.getString(R.string.writing_style), ratingModel.getWritingStyle(), isNewBook);
        bookDetailsItemList.add(valueWritingStyle);

        BookDetailsItem valueOverall = new BookDetailsItem();
        setStarRating(valueOverall, resourceHelper.getString(R.string.overall_rating), ratingModel.getOverallRating(), isNewBook);
        bookDetailsItemList.add(valueOverall);

        _bookDetailsList.setValue(bookDetailsItemList);

    }

    private void setEditableText(BookDetailsItem editableText, boolean isCenter, String value, boolean isEditable) {
        editableText.setItemViewType(BookDetailsItem.ItemViewType.EDITABLE_TEXT);
        editableText.setCenter(isCenter);
        editableText.setValue(value);
        editableText.setEditable(isEditable);
    }

    private void setSelectedValue(BookDetailsItem options, List<String> optionsList, String selected, boolean isEditable) {
        options.setItemViewType(BookDetailsItem.ItemViewType.POP_UP);
        options.setSingleSelection(optionsList);
        options.setSelectedValue(selected);
        options.setEditable(isEditable);
    }

    private void setHeader(BookDetailsItem header, String title) {
        header.setTitle(title);
        header.setItemViewType(BookDetailsItem.ItemViewType.HEADER);
    }

    private void setStarRating(BookDetailsItem starRating, String title, float rating, boolean isEditable) {
        starRating.setItemViewType(BookDetailsItem.ItemViewType.STAR_RATING);
        starRating.setTitle(title);
        starRating.setRating(rating);
        starRating.setEditable(isEditable);
    }

    public void onItemClicked(int position) {
        updateBookDetails(position, true, null, null, null, null);
    }

    public void onRatingChanged(float rating, int position) {
        updateBookDetails(position, false, rating, null, null, null);
    }


    public void onTextChanged(String text, int position) {
        updateBookDetails(position, false, null, text, null, null);
    }

    public void onDateChanged(String date, int position) {
        updateBookDetails(position, false, null, null, date, null);
    }

    public void onOptionChanged(String selected, int position) {
        updateBookDetails(position, false, null, null, null, selected);
    }

    private void updateBookDetails(int position, boolean editable, Float rating, String text, String date, String selected) {
        if (position >= 0 && position < bookDetailsItemList.size()) {
            BookDetailsItem item = bookDetailsItemList.get(position);
            item.setEditable(editable);
            if (rating != null) {
                item.setRating(rating);
            }
            if (text != null) {
                item.setValue(text);
            }
            if (date != null) {
                item.setDate(date);
            }
            if (selected != null) {
                item.setSelectedValue(selected);
            }
            _bookDetailsList.setValue(bookDetailsItemList);
            _updateDetailItem.setValue(position);
        }
    }


    private void search(String query, SearchType searchType) {
        Call<BookResponse> call = null;
        _progressBarVisibility.setValue(View.VISIBLE);
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
                BookResponse bookResponse = response.body();
                _progressBarVisibility.setValue(View.GONE);
                if (bookResponse != null && bookResponse.getNumFound() > 0) {
                    BookModel searchedBook = bookModelCreator.convertToBook(bookResponse);
                    createBookDetailsList(searchedBook, true);
                } else {
                    _onShowBookNotFound.setValue(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookResponse> call, @NonNull Throwable t) {
            }
        });
    }
}

