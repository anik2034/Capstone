package com.anik.capstone.bookDetails;


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
import com.anik.capstone.util.ResourceHelper;

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

    private ResourceHelper resourceHelper;
    private final List<BookDetailsModel> bookDetailsModelList = new ArrayList<>();

    @Inject
    public BookDetailsViewModel(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
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
            setHeader(headerGenre, resourceHelper.getString(R.string.genre));
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
            setHeader(headerReadingStatus, resourceHelper.getString(R.string.reading_status));
            setSelectedValue(valueReadingStatus, ReadingStatus.getAllDisplayNames(), bookModel.getReadingStatus().getDisplayName(), isNewBook);
            bookDetailsModelList.add(headerReadingStatus);
            bookDetailsModelList.add(valueReadingStatus);
        }
        if (borrowingModel != null) {
            BookDetailsModel headerBorrowingStatus = new BookDetailsModel();
            BookDetailsModel valueBorrowingStatus = new BookDetailsModel();
            setHeader(headerBorrowingStatus, resourceHelper.getString(R.string.borrowing_status));
            setSelectedValue(valueBorrowingStatus, BorrowingStatus.getAllDisplayNames(), borrowingModel.getBorrowingStatus().getDisplayName(), isNewBook);
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
                valueBorrowingDate.setEditable(isNewBook);
                bookDetailsModelList.add(valueBorrowingDate);
            }
        }
        if (ratingModel != null) {
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
        }
        _bookDetailsList.setValue(bookDetailsModelList);
    }

    private void setEditableText(BookDetailsModel editableText, String value, boolean isEditable) {
        editableText.setItemViewType(BookDetailsModel.ItemViewType.EDITABLE_TEXT);
        editableText.setValue(value);
        editableText.setEditable(isEditable);
    }

    private void setSelectedValue(BookDetailsModel options, List<String> optionsList, String selected, boolean isEditable) {
        options.setItemViewType(BookDetailsModel.ItemViewType.SPINNER);
        options.setOptions(optionsList);
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
        if (_bookDetailsList.getValue() != null) {
            BookDetailsModel item = bookDetailsModelList.get(position);
            item.setEditable(true);
            _bookDetailsList.setValue(bookDetailsModelList);
            _updateDetailItem.setValue(position);
        }
    }

    public void onRatingChanged(float rating, int position) {
        if (_bookDetailsList.getValue() != null) {
            BookDetailsModel item = bookDetailsModelList.get(position);
            item.setEditable(false);
            item.setRating(rating);
            _bookDetailsList.setValue(bookDetailsModelList);
            _updateDetailItem.setValue(position);
        }
    }


    public void onTextChanged(String text, int position) {
        if (_bookDetailsList.getValue() != null) {
            BookDetailsModel item = bookDetailsModelList.get(position);
            item.setEditable(false);
            item.setValue(text);
            _bookDetailsList.setValue(bookDetailsModelList);
            _updateDetailItem.setValue(position);
        }
    }

    public void onDateChanged(String date, int position) {
        if (_bookDetailsList.getValue() != null) {
            BookDetailsModel item = bookDetailsModelList.get(position);
            item.setEditable(false);
            item.setDate(date);
            _bookDetailsList.setValue(bookDetailsModelList);
            _updateDetailItem.setValue(position);
        }
    }

    public void onOptionChanged(String selected, int position) {
        if (_bookDetailsList.getValue() != null) {
            BookDetailsModel item = bookDetailsModelList.get(position);
            item.setEditable(false);
            item.setSelectedValue(selected);
            _bookDetailsList.setValue(bookDetailsModelList);
            _updateDetailItem.setValue(position);
        }
    }
}
