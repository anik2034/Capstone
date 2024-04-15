package com.anik.capstone.bookDetails;


import android.opengl.Visibility;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.anik.capstone.R;
import com.anik.capstone.model.BookDetailsModel;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ReadingStatus;
import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.borrowing.BorrowingStatus;
import com.anik.capstone.model.rating.RatingModel;
import com.anik.capstone.network.BookSearchCallback;
import com.anik.capstone.network.RetrofitClient;
import com.anik.capstone.network.responses.BookMaker;
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

    private final MutableLiveData<List<BookDetailsModel>> _bookDetailsList = new MutableLiveData<>();
    public LiveData<List<BookDetailsModel>> bookDetailsList = _bookDetailsList;

    private final MutableLiveData<Integer> _progressBarVisibility = new MutableLiveData<>();
    public LiveData<Integer> progressBarVisibility = _progressBarVisibility;


    private final MutableLiveData<String> _bookTitle = new MutableLiveData<>();
    public LiveData<String> bookTitle = _bookTitle;

    private final MutableLiveData<String> _bookAuthor = new MutableLiveData<>();
    public LiveData<String> bookAuthor = _bookAuthor;

    private final MutableLiveData<Integer> _updateDetailItem = new MutableLiveData<>();
    public LiveData<Integer> updateDetailItem = _updateDetailItem;

    private ResourceHelper resourceHelper;
    private final List<BookDetailsModel> bookDetailsModelList = new ArrayList<>();

    @Inject
    public BookDetailsViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public void init(BookModel bookModel, boolean isNewBook) {
        _progressBarVisibility.setValue(View.GONE);
        setBookDetail(bookModel);
        createBookDetailsList(bookModel, isNewBook);
    }

    private void setBookDetail(BookModel bookModel) {
        if (bookModel != null) {
            _bookTitle.setValue(bookModel.getTitle());
            _bookAuthor.setValue(bookModel.getAuthor());
        }
    }

    private void createBookDetailsList(BookModel bookModel, boolean isNewBook) {
        BorrowingModel borrowingModel = new BorrowingModel(BorrowingStatus.NOT_BORROWED, resourceHelper.getString(R.string.borrowed_by), "");
        ReadingStatus readingStatus = ReadingStatus.NOT_STARTED;
        RatingModel ratingModel = new RatingModel();
        String url = "sample";
        List<String> genres = Arrays.asList("Genre 1", "Genre 2");
        if (bookModel != null) {
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
                if(borrowingModel.getBorrowingStatus() == null) borrowingModel.setBorrowingStatus(BorrowingStatus.NOT_BORROWED);
                if(borrowingModel.getDate() == null) borrowingModel.setDate("");
                if(borrowingModel.getName() == null) borrowingModel.setName(resourceHelper.getString(R.string.borrowed_by));
            }
            if (bookModel.getRating() != null) {
                ratingModel = bookModel.getRating();
            }
        }

        BookDetailsModel valueUrl = new BookDetailsModel();
        valueUrl.setThumbnailUrl(url);
        valueUrl.setItemViewType(BookDetailsModel.ItemViewType.THUMBNAIL);
        bookDetailsModelList.add(valueUrl);

        for (String genre : genres) {
            BookDetailsModel headerGenre = new BookDetailsModel();
            BookDetailsModel valueGenre = new BookDetailsModel();
            setHeader(headerGenre, resourceHelper.getString(R.string.genre));
            setEditableText(valueGenre, genre, isNewBook);
            bookDetailsModelList.add(headerGenre);
            bookDetailsModelList.add(valueGenre);
        }

        BookDetailsModel headerReadingStatus = new BookDetailsModel();
        BookDetailsModel valueReadingStatus = new BookDetailsModel();
        setHeader(headerReadingStatus, resourceHelper.getString(R.string.reading_status));
        setSelectedValue(valueReadingStatus, ReadingStatus.getAllDisplayNames(), readingStatus.getDisplayName(), isNewBook);
        bookDetailsModelList.add(headerReadingStatus);
        bookDetailsModelList.add(valueReadingStatus);

        BookDetailsModel headerBorrowingStatus = new BookDetailsModel();
        BookDetailsModel valueBorrowingStatus = new BookDetailsModel();
        setHeader(headerBorrowingStatus, resourceHelper.getString(R.string.borrowing_status));
        setSelectedValue(valueBorrowingStatus, BorrowingStatus.getAllDisplayNames(), borrowingModel.getBorrowingStatus().getDisplayName(), isNewBook);
        bookDetailsModelList.add(headerBorrowingStatus);
        bookDetailsModelList.add(valueBorrowingStatus);

        BookDetailsModel valueBorrowingName = new BookDetailsModel();
        setEditableText(valueBorrowingName, borrowingModel.getName(), isNewBook);
        bookDetailsModelList.add(valueBorrowingName);

        BookDetailsModel valueBorrowingDate = new BookDetailsModel();
        valueBorrowingDate.setItemViewType(BookDetailsModel.ItemViewType.DATE);
        valueBorrowingDate.setDate(borrowingModel.getDate());
        valueBorrowingDate.setEditable(isNewBook);
        bookDetailsModelList.add(valueBorrowingDate);

        BookDetailsModel headerRating = new BookDetailsModel();
        setHeader(headerRating, resourceHelper.getString(R.string.rating));
        bookDetailsModelList.add(headerRating);

        BookDetailsModel valueEmotionalImpact = new BookDetailsModel();
        setStarRating(valueEmotionalImpact, resourceHelper.getString(R.string.emotional_impact), ratingModel.getEmotionalImpact(), isNewBook);
        bookDetailsModelList.add(valueEmotionalImpact);

        BookDetailsModel valueCharacter = new BookDetailsModel();
        setStarRating(valueCharacter, resourceHelper.getString(R.string.characters), ratingModel.getCharacter(), isNewBook);
        bookDetailsModelList.add(valueCharacter);

        BookDetailsModel valuePacing = new BookDetailsModel();
        setStarRating(valuePacing, resourceHelper.getString(R.string.pacing), ratingModel.getPacing(), isNewBook);
        bookDetailsModelList.add(valuePacing);

        BookDetailsModel valueStoryLine = new BookDetailsModel();
        setStarRating(valueStoryLine, resourceHelper.getString(R.string.story_line), ratingModel.getStoryline(), isNewBook);
        bookDetailsModelList.add(valueStoryLine);

        BookDetailsModel valueWritingStyle = new BookDetailsModel();
        setStarRating(valueWritingStyle, resourceHelper.getString(R.string.writing_style), ratingModel.getWritingStyle(), isNewBook);
        bookDetailsModelList.add(valueWritingStyle);

        BookDetailsModel valueOverall = new BookDetailsModel();
        setStarRating(valueOverall, resourceHelper.getString(R.string.overall_rating), ratingModel.getOverallRating(), isNewBook);
        bookDetailsModelList.add(valueOverall);

        _bookDetailsList.setValue(bookDetailsModelList);

    }

    private void setEditableText(BookDetailsModel editableText, String value, boolean isEditable) {
        editableText.setItemViewType(BookDetailsModel.ItemViewType.EDITABLE_TEXT);
        editableText.setValue(value);
        editableText.setEditable(isEditable);
    }

    private void setSelectedValue(BookDetailsModel options, List<String> optionsList, String selected, boolean isEditable) {
        options.setItemViewType(BookDetailsModel.ItemViewType.POP_UP);
        options.setSingleSelection(optionsList);
        options.setSelectedValue(selected);
        options.setEditable(isEditable);
    }

    private void setHeader(BookDetailsModel header, String title) {
        header.setTitle(title);
        header.setItemViewType(BookDetailsModel.ItemViewType.HEADER);
    }

    private void setStarRating(BookDetailsModel starRating, String title, float rating, boolean isEditable) {
        starRating.setItemViewType(BookDetailsModel.ItemViewType.STAR_RATING);
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
        if (position >= 0 && position < bookDetailsModelList.size()) {
            BookDetailsModel item = bookDetailsModelList.get(position);
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
            _bookDetailsList.setValue(bookDetailsModelList);
            _updateDetailItem.setValue(position);
        }
    }

    public void search(String query, String searchType, BookSearchCallback callback) {
        Call<BookResponse> call = null;
        _progressBarVisibility.setValue(View.VISIBLE);
        if (searchType.equals(BookDetailsFragment.ARG_SEARCH_ISBN)) {
            call = RetrofitClient.bookService.searchByISBN(query);
        } else if (searchType.equals(BookDetailsFragment.ARG_SEARCH_TITLE)) {
            call = RetrofitClient.bookService.searchByTitle(query);
        }

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookResponse> call, @NonNull Response<BookResponse> response) {
                BookResponse bookResponse = response.body();
                _progressBarVisibility.setValue(View.GONE);
                if (bookResponse != null && bookResponse.getNumFound() > 0) {
                    BookModel bookModel = BookMaker.convertToBook(bookResponse);
                    callback.onBookFound(bookModel);
                } else {
                    callback.onSearchFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookResponse> call, @NonNull Throwable t) {
                callback.onSearchFailed();
            }
        });
    }
}
