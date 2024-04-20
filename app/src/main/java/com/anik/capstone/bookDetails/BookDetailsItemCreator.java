package com.anik.capstone.bookDetails;

import com.anik.capstone.R;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.ReadingStatus;
import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.borrowing.BorrowingStatus;
import com.anik.capstone.model.rating.RatingModel;
import com.anik.capstone.util.ResourceHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class BookDetailsItemCreator {

    private final ResourceHelper resourceHelper;

    @Inject
    public BookDetailsItemCreator(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public List<BookDetailsItem> create(BookModel bookModel, boolean isNewBook) {
        List<BookDetailsItem> bookDetailsItemList = new ArrayList<>();
        BorrowingModel borrowingModel = new BorrowingModel(BorrowingStatus.NOT_BORROWED, "", "");
        ReadingStatus readingStatus = ReadingStatus.NOT_STARTED;
        RatingModel ratingModel = new RatingModel();
        // TODO move to strings.xml
        String url = "sample";
        String title = "Title";
        String author = "Author";
        List<String> genres = Arrays.asList("Genre 1", "Genre 2");
        int bookModelId = -1;
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
            bookModelId = bookModel.getId();
        }
        BookDetailsItem valueTitle = new BookDetailsItem();
        setEditableText(valueTitle, true, title, isNewBook);
        valueTitle.setBookModelId(bookModelId);
        bookDetailsItemList.add(valueTitle);

        BookDetailsItem valueAuthor = new BookDetailsItem();
        setEditableText(valueAuthor, true, author, isNewBook);
        bookDetailsItemList.add(valueAuthor);

        BookDetailsItem valueUrl = new BookDetailsItem();
        valueUrl.setThumbnailUrl(url);
        valueUrl.setItemViewType(BookDetailsItem.ViewType.THUMBNAIL);
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
        valueBorrowingDate.setItemViewType(BookDetailsItem.ViewType.DATE);
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

        return bookDetailsItemList;
    }

    private void setEditableText(BookDetailsItem editableText, boolean isCenter, String value, boolean isEditable) {
        editableText.setItemViewType(BookDetailsItem.ViewType.EDITABLE_TEXT);
        editableText.setCenter(isCenter);
        editableText.setValue(value);
        editableText.setEditable(isEditable);
    }

    private void setSelectedValue(BookDetailsItem options, List<String> optionsList, String selected, boolean isEditable) {
        options.setItemViewType(BookDetailsItem.ViewType.POP_UP);
        options.setSingleSelection(optionsList);
        options.setSelectedValue(selected);
        options.setEditable(isEditable);
    }

    private void setHeader(BookDetailsItem header, String title) {
        header.setTitle(title);
        header.setItemViewType(BookDetailsItem.ViewType.HEADER);
    }

    private void setStarRating(BookDetailsItem starRating, String title, float rating, boolean isEditable) {
        starRating.setItemViewType(BookDetailsItem.ViewType.STAR_RATING);
        starRating.setTitle(title);
        starRating.setRating(rating);
        starRating.setEditable(isEditable);
    }
}
