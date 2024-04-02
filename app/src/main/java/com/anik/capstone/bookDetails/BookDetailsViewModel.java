package com.anik.capstone.bookDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anik.capstone.model.BookDetailsModel;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.rating.RatingModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookDetailsViewModel extends ViewModel {
    
    private final MutableLiveData<List<BookDetailsModel>> _bookDetailsList = new MutableLiveData<>();
    public LiveData<List<BookDetailsModel>> bookDetailsList = _bookDetailsList;
    private final MutableLiveData<String> _bookTitle = new MutableLiveData<>();
    public LiveData<String> bookTitle = _bookTitle;
    private final MutableLiveData<String> _bookAuthor = new MutableLiveData<>();
    public LiveData<String> bookAuthor = _bookAuthor;
    private final MutableLiveData<Integer> _updateDetailItem = new MutableLiveData<>();
    public LiveData<Integer> updateDetailItem = _updateDetailItem;
    
    private final List<BookDetailsModel> bookDetailsModelList = new ArrayList<>();
    
    @Inject
    public BookDetailsViewModel() {
    }

    public void init(BookModel bookModel, boolean isNewBook) {
        setBookDetail(bookModel);
        createBookDetailsList(bookModel, isNewBook);
    }
    
    private void setBookDetail(BookModel bookModel) {
        _bookTitle.setValue(bookModel.getTitle());
        _bookAuthor.setValue(bookModel.getAuthor());
    }

    private void createBookDetailsList(BookModel bookModel, boolean isNewBook) {
        BorrowingModel borrowingModel = bookModel.getBorrowingModel();
        RatingModel ratingModel = bookModel.getRating();
        if (bookModel.getCoverUrl() != null) {
            BookDetailsModel valueUrl = new BookDetailsModel();
            valueUrl.setThumbnailUrl(bookModel.getCoverUrl());
            valueUrl.setItemViewType(BookDetailsModel.ItemViewType.THUMBNAIL);
            bookDetailsModelList.add(valueUrl);
        }
        if (bookModel.getGenre() != null) {
            BookDetailsModel headerGenre = new BookDetailsModel();
            BookDetailsModel valueGenre = new BookDetailsModel();
            setHeader(headerGenre, "Genre");
            setEditableText(valueGenre, bookModel.getGenre(), isNewBook);
            bookDetailsModelList.add(headerGenre);
            bookDetailsModelList.add(valueGenre);
        }
        if (bookModel.getCoverUrl() != null) {
            BookDetailsModel valueUrl = new BookDetailsModel();
            valueUrl.setItemViewType(BookDetailsModel.ItemViewType.THUMBNAIL);
            valueUrl.setThumbnailUrl(bookModel.getCoverUrl());
        }
        if (bookModel.getReadingStatus() != null) {
            BookDetailsModel headerReadingStatus = new BookDetailsModel();
            BookDetailsModel valueReadingStatus = new BookDetailsModel();
            setHeader(headerReadingStatus, "Reading Status");
            setSelectedValue(valueReadingStatus, bookModel.getReadingStatus().name(), isNewBook);
            bookDetailsModelList.add(headerReadingStatus);
            bookDetailsModelList.add(valueReadingStatus);
        }
        if (borrowingModel != null) {
            BookDetailsModel headerBorrowingStatus = new BookDetailsModel();
            BookDetailsModel valueBorrowingStatus = new BookDetailsModel();
            setHeader(headerBorrowingStatus, "Borrowing Status");
            setSelectedValue(valueBorrowingStatus, borrowingModel.getBorrowingStatus().name(), isNewBook);
            bookDetailsModelList.add(headerBorrowingStatus);
            bookDetailsModelList.add(valueBorrowingStatus);
            if (borrowingModel.getName() != null) {
                BookDetailsModel valueBorrowingName = new BookDetailsModel();
                setEditableText(valueBorrowingName, borrowingModel.getName(), isNewBook);
                bookDetailsModelList.add(valueBorrowingName);
            }
            if (borrowingModel.getDate() != null) {
                BookDetailsModel valueBorrowingDate = new BookDetailsModel();
                valueBorrowingDate.setItemViewType(BookDetailsModel.ItemViewType.DATE);
                valueBorrowingDate.setDate(borrowingModel.getDate());
                valueBorrowingDate.setIsEditable(isNewBook);
                bookDetailsModelList.add(valueBorrowingDate);
            }
        }
        if (ratingModel != null) {
            BookDetailsModel headerRating = new BookDetailsModel();
            setHeader(headerRating, "Rating");
            bookDetailsModelList.add(headerRating);

            BookDetailsModel valueEmotionalImpact = new BookDetailsModel();
            setStarRating(valueEmotionalImpact, "Emotional Impact", ratingModel.getEmotionalImpact(), isNewBook);
            bookDetailsModelList.add(valueEmotionalImpact);

            BookDetailsModel valueCharacter = new BookDetailsModel();
            setStarRating(valueCharacter, "Characters", ratingModel.getCharacter(), isNewBook);
            bookDetailsModelList.add(valueCharacter);

            BookDetailsModel valuePacing = new BookDetailsModel();
            setStarRating(valuePacing, "Pacing", ratingModel.getPacing(), isNewBook);
            bookDetailsModelList.add(valuePacing);

            BookDetailsModel valueStoryLine = new BookDetailsModel();
            setStarRating(valueStoryLine, "Story Line", ratingModel.getStoryline(), isNewBook);
            bookDetailsModelList.add(valueStoryLine);

            BookDetailsModel valueWritingStyle = new BookDetailsModel();
            setStarRating(valueWritingStyle, "Writing Style", ratingModel.getWritingStyle(), isNewBook);
            bookDetailsModelList.add(valueWritingStyle);

            BookDetailsModel valueOverall = new BookDetailsModel();
            setStarRating(valueOverall, "Overall Rating", ratingModel.getOverallRating(), isNewBook);
            bookDetailsModelList.add(valueOverall);
        }
        _bookDetailsList.setValue(bookDetailsModelList);
    }

    private void setEditableText(BookDetailsModel editableText, String value, boolean isEditable) {
        editableText.setItemViewType(BookDetailsModel.ItemViewType.EDITABLE_TEXT);
        editableText.setValue(value);
        editableText.setIsEditable(isEditable);
    }

    private void setSelectedValue(BookDetailsModel spinner, String value, boolean isEditable) {
        spinner.setItemViewType(BookDetailsModel.ItemViewType.SPINNER);
        spinner.setSelectedValue(value);
        spinner.setIsEditable(isEditable);
    }

    private void setHeader(BookDetailsModel header, String title) {
        header.setTitle(title);
        header.setItemViewType(BookDetailsModel.ItemViewType.HEADER);
    }

    private void setStarRating(BookDetailsModel starRating, String title, float rating, boolean isEditable) {
        starRating.setItemViewType(BookDetailsModel.ItemViewType.STAR_RATING);
        starRating.setTitle(title);
        starRating.setRating(rating);
        starRating.setIsEditable(isEditable);
    }

    public void onItemClicked(int position) {
        if (_bookDetailsList.getValue() != null) {
            BookDetailsModel item = bookDetailsModelList.get(position);
            item.setIsEditable(true);
            _bookDetailsList.setValue(bookDetailsModelList);
            _updateDetailItem.setValue(position);
        }
    }

    public void onRatingChanged(float rating, BookDetailsModel bookDetailsModel) {

    }
}
