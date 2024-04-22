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

import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.AUTHOR;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.BORROWED_BY;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.BORROWING_DATE;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.BORROWING_STATUS;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.GENRE;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.OVERALL_RATING;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.RATING_CHARACTERS;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.RATING_EMOTIONAL_IMPACT;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.RATING_PACING;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.RATING_STORYLINE;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.RATING_WRITING_STYLE;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.READING_STATUS;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.THUMBNAIL;
import static com.anik.capstone.bookDetails.BookDetailsItem.ItemType.TITLE;

public class BookDetailsItemCreator {

    private final ResourceHelper resourceHelper;

    @Inject
    public BookDetailsItemCreator(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    public List<BookDetailsItem> create(BookModel bookModel, boolean isNewBook) {
        List<BookDetailsItem> bookDetailsItemList = new ArrayList<>();
        BorrowingModel borrowingModel = bookModel.getBorrowing();
        ReadingStatus readingStatus = bookModel.getReadingStatus();
        RatingModel ratingModel = bookModel.getRating();
        String url = bookModel.getCoverUrl();
        String title = bookModel.getTitle();
        String author = bookModel.getAuthor();
        List<String> genres = bookModel.getGenres();
        int bookModelId = bookModel.getId();
        
        BookDetailsItem valueTitle = new BookDetailsItem();
        setEditableText(valueTitle, title, resourceHelper.getString(R.string.title), isNewBook);
        valueTitle.setBookModelId(bookModelId);

        valueTitle.setItemType(TITLE);
        bookDetailsItemList.add(valueTitle);

        BookDetailsItem valueAuthor = new BookDetailsItem();
        setEditableText(valueAuthor, author, resourceHelper.getString(R.string.author), isNewBook);
        valueAuthor.setItemType(AUTHOR);
        valueAuthor.setBookModelId(bookModelId);
        bookDetailsItemList.add(valueAuthor);

        BookDetailsItem valueUrl = new BookDetailsItem();
        valueUrl.setThumbnailUrl(url);
        valueUrl.setItemViewType(BookDetailsItem.ViewType.THUMBNAIL);
        valueUrl.setBookModelId(bookModelId);
        valueUrl.setItemType(THUMBNAIL);
        bookDetailsItemList.add(valueUrl);

        for (String genre : genres) {
            BookDetailsItem headerGenre = new BookDetailsItem();
            BookDetailsItem valueGenre = new BookDetailsItem();
            setHeader(headerGenre, resourceHelper.getString(R.string.genre));
            setEditableText(valueGenre, genre, resourceHelper.getString(R.string.genre), isNewBook);
            valueGenre.setItemType(GENRE);
            valueGenre.setBookModelId(bookModelId);
            bookDetailsItemList.add(headerGenre);
            bookDetailsItemList.add(valueGenre);
        }

        BookDetailsItem headerReadingStatus = new BookDetailsItem();
        BookDetailsItem valueReadingStatus = new BookDetailsItem();
        setHeader(headerReadingStatus, resourceHelper.getString(R.string.reading_status));
        setSelectedValue(valueReadingStatus, ReadingStatus.getAllDisplayNames(), readingStatus.getDisplayName(), isNewBook);
        valueReadingStatus.setItemType(READING_STATUS);
        valueReadingStatus.setBookModelId(bookModelId);
        bookDetailsItemList.add(headerReadingStatus);
        bookDetailsItemList.add(valueReadingStatus);

        BookDetailsItem headerBorrowingStatus = new BookDetailsItem();
        BookDetailsItem valueBorrowingStatus = new BookDetailsItem();
        setHeader(headerBorrowingStatus, resourceHelper.getString(R.string.borrowing_status));
        setSelectedValue(valueBorrowingStatus, BorrowingStatus.getAllDisplayNames(), borrowingModel.getBorrowingStatus().getDisplayName(), isNewBook);
        valueBorrowingStatus.setItemType(BORROWING_STATUS);
        valueBorrowingStatus.setBookModelId(bookModelId);
        bookDetailsItemList.add(headerBorrowingStatus);
        bookDetailsItemList.add(valueBorrowingStatus);

        BookDetailsItem headerBorrowingDetails = new BookDetailsItem();
        setHeader(headerBorrowingDetails, resourceHelper.getString(R.string.borrowing_details));

        BookDetailsItem valueBorrowingName = new BookDetailsItem();
        setEditableText(valueBorrowingName, borrowingModel.getName(), resourceHelper.getString(R.string.name), isNewBook);
        valueBorrowingName.setItemType(BORROWED_BY);
        valueBorrowingName.setBookModelId(bookModelId);
        bookDetailsItemList.add(headerBorrowingDetails);
        bookDetailsItemList.add(valueBorrowingName);

        BookDetailsItem valueBorrowingDate = new BookDetailsItem();
        valueBorrowingDate.setItemViewType(BookDetailsItem.ViewType.DATE);
        valueBorrowingDate.setDate(borrowingModel.getDate());
        valueBorrowingDate.setEditable(isNewBook);
        valueBorrowingDate.setBookModelId(bookModelId);
        valueBorrowingDate.setItemType(BORROWING_DATE);
        bookDetailsItemList.add(valueBorrowingDate);

        BookDetailsItem headerRating = new BookDetailsItem();
        setHeader(headerRating, resourceHelper.getString(R.string.rating));
        bookDetailsItemList.add(headerRating);

        BookDetailsItem valueEmotionalImpact = new BookDetailsItem();
        setStarRating(valueEmotionalImpact, resourceHelper.getString(R.string.emotional_impact), ratingModel.getEmotionalImpact(), isNewBook);
        valueEmotionalImpact.setItemType(RATING_EMOTIONAL_IMPACT);
        valueEmotionalImpact.setBookModelId(bookModelId);
        bookDetailsItemList.add(valueEmotionalImpact);

        BookDetailsItem valueCharacter = new BookDetailsItem();
        setStarRating(valueCharacter, resourceHelper.getString(R.string.characters), ratingModel.getCharacter(), isNewBook);
        valueCharacter.setItemType(RATING_CHARACTERS);
        valueCharacter.setBookModelId(bookModelId);
        bookDetailsItemList.add(valueCharacter);

        BookDetailsItem valuePacing = new BookDetailsItem();
        setStarRating(valuePacing, resourceHelper.getString(R.string.pacing), ratingModel.getPacing(), isNewBook);
        valuePacing.setItemType(RATING_PACING);
        valuePacing.setBookModelId(bookModelId);
        bookDetailsItemList.add(valuePacing);

        BookDetailsItem valueStoryLine = new BookDetailsItem();
        setStarRating(valueStoryLine, resourceHelper.getString(R.string.story_line), ratingModel.getStoryline(), isNewBook);
        valueStoryLine.setItemType(RATING_STORYLINE);
        valueStoryLine.setBookModelId(bookModelId);
        bookDetailsItemList.add(valueStoryLine);

        BookDetailsItem valueWritingStyle = new BookDetailsItem();
        setStarRating(valueWritingStyle, resourceHelper.getString(R.string.writing_style), ratingModel.getWritingStyle(), isNewBook);
        valueWritingStyle.setItemType(RATING_WRITING_STYLE);
        valueWritingStyle.setBookModelId(bookModelId);
        bookDetailsItemList.add(valueWritingStyle);

        BookDetailsItem valueOverall = new BookDetailsItem();
        setStarRating(valueOverall, resourceHelper.getString(R.string.overall_rating), ratingModel.getOverallRating(), isNewBook);
        valueOverall.setItemType(OVERALL_RATING);
        valueOverall.setBookModelId(bookModelId);
        bookDetailsItemList.add(valueOverall);

        return bookDetailsItemList;
    }

    private void setEditableText(BookDetailsItem editableText, String value, String hint, boolean isEditable) {
        editableText.setItemViewType(BookDetailsItem.ViewType.EDITABLE_TEXT);
        editableText.setValue(value);
        editableText.setHint(hint);
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
