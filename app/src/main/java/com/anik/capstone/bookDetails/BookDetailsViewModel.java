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

    private final MutableLiveData<BookModel> _bookModel = new MutableLiveData<>();
    private final MutableLiveData<List<BookDetailsModel>> _bookDetailsList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isNewBook = new MutableLiveData<>();
    public LiveData<BookModel> bookModel = _bookModel;
    public LiveData<List<BookDetailsModel>> bookDetailsList = _bookDetailsList;
    public LiveData<Boolean> isNewBook = _isNewBook;


    @Inject
    public BookDetailsViewModel() {
    }

    public void init(BookModel bookModel, Boolean isNewBook) {
        _bookModel.setValue(bookModel);
        _isNewBook.setValue(isNewBook);
        _bookDetailsList.setValue(bookModelToBookDetailsList());
    }
    private List<BookDetailsModel> bookModelToBookDetailsList() {
        List<BookDetailsModel> list = new ArrayList<>();

        BookModel bookModel = _bookModel.getValue();
        BorrowingModel borrowingModel = bookModel.getBorrowingModel();
        RatingModel ratingModel = bookModel.getRating();
        if (bookModel.getCoverUrl() != null) {
            BookDetailsModel valueUrl = new BookDetailsModel();
            valueUrl.setThumbnailUrl(bookModel.getCoverUrl());
            valueUrl.setItemViewType(BookDetailsModel.ItemViewType.THUMBNAIL);
            list.add(valueUrl);

        }
        if (bookModel.getGenre() != null) {
            BookDetailsModel headerGenre = new BookDetailsModel();
            BookDetailsModel valueGenre = new BookDetailsModel();
            setHeader(headerGenre, "Genre");
            setEditableText(valueGenre, bookModel.getGenre(), _isNewBook.getValue());
            list.add(headerGenre);
            list.add(valueGenre);
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
            setSelectedValue(valueReadingStatus, bookModel.getReadingStatus().name(), _isNewBook.getValue());
            list.add(headerReadingStatus);
            list.add(valueReadingStatus);
        }
        if (borrowingModel != null) {
            BookDetailsModel headerBorrowingStatus = new BookDetailsModel();
            BookDetailsModel valueBorrowingStatus = new BookDetailsModel();
            setHeader(headerBorrowingStatus, "Borrowing Status");
            setSelectedValue(valueBorrowingStatus, borrowingModel.getBorrowingStatus().name(), _isNewBook.getValue());
            list.add(headerBorrowingStatus);
            list.add(valueBorrowingStatus);
            if (borrowingModel.getName() != null) {
                BookDetailsModel valueBorrowingName = new BookDetailsModel();
                setEditableText(valueBorrowingName, borrowingModel.getName(), _isNewBook.getValue());
                list.add(valueBorrowingName);
            }
            if (borrowingModel.getDate() != null) {
                BookDetailsModel valueBorrowingDate = new BookDetailsModel();
                valueBorrowingDate.setItemViewType(BookDetailsModel.ItemViewType.DATE);
                valueBorrowingDate.setDate(borrowingModel.getDate());
                valueBorrowingDate.setIsEditable(_isNewBook.getValue());
                list.add(valueBorrowingDate);
            }
        }
        if (ratingModel != null) {
            BookDetailsModel headerRating = new BookDetailsModel();
            setHeader(headerRating, "Rating");
            list.add(headerRating);

            BookDetailsModel valueEmotionalImpact = new BookDetailsModel();
            setStarRating(valueEmotionalImpact, "Emotional Impact", ratingModel.getEmotionalImpact(), _isNewBook.getValue());
            list.add(valueEmotionalImpact);

            BookDetailsModel valueCharacter = new BookDetailsModel();
            setStarRating(valueCharacter, "Characters", ratingModel.getCharacter(), _isNewBook.getValue());
            list.add(valueCharacter);

            BookDetailsModel valuePacing = new BookDetailsModel();
            setStarRating(valuePacing, "Pacing", ratingModel.getPacing(), _isNewBook.getValue());
            list.add(valuePacing);

            BookDetailsModel valueStoryLine = new BookDetailsModel();
            setStarRating(valueStoryLine, "Story Line", ratingModel.getStoryline(), _isNewBook.getValue());
            list.add(valueStoryLine);

            BookDetailsModel valueWritingStyle = new BookDetailsModel();
            setStarRating(valueWritingStyle, "Writing Style", ratingModel.getWritingStyle(), _isNewBook.getValue());
            list.add(valueWritingStyle);

            BookDetailsModel valueOverall = new BookDetailsModel();
            setStarRating(valueOverall, "Overall Rating", ratingModel.getOverallRating(), _isNewBook.getValue());
            list.add(valueOverall);
        }
        return list;
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

    public void onItemClicked(BookDetailsModel item){
        item.setIsEditable(true);
    }
}
