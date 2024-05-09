package com.anik.capstone.bookDetails;

import java.util.List;
import java.util.Objects;

public class BookDetailsItem {
    private String id;
    private String title;
    private String value;
    private String selectedValue;
    private float rating;
    private String date;
    private ViewType viewType;
    private ItemType itemType;
    private String thumbnailUrl;
    private boolean isEditable;
    private List<String> singleSelection;
    private int bookModelId;
    private String hint;

    public BookDetailsItem() {
    }

    public BookDetailsItem(BookDetailsItem bookDetailsItem) {
        this.id = bookDetailsItem.id;
        this.title = bookDetailsItem.title;
        this.value = bookDetailsItem.value;
        this.selectedValue = bookDetailsItem.selectedValue;
        this.rating = bookDetailsItem.rating;
        this.date = bookDetailsItem.date;
        this.viewType = bookDetailsItem.viewType;
        this.itemType = bookDetailsItem.itemType;
        this.thumbnailUrl = bookDetailsItem.thumbnailUrl;
        this.isEditable = bookDetailsItem.isEditable;
        this.singleSelection = bookDetailsItem.singleSelection;
        this.bookModelId = bookDetailsItem.bookModelId;
        this.hint = bookDetailsItem.hint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public List<String> getSingleSelection() {
        return singleSelection;
    }

    public void setSingleSelection(List<String> singleSelection) {
        this.singleSelection = singleSelection;
    }

    public int getBookModelId() {
        return bookModelId;
    }

    public void setBookModelId(int bookModelId) {
        this.bookModelId = bookModelId;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDetailsItem that = (BookDetailsItem) o;
        return Objects.equals(id, that.id) && isEditable == that.isEditable && viewType == that.viewType && itemType == that.itemType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isEditable, viewType, itemType);
    }

    public enum ViewType {
        HEADER,
        EDITABLE_TEXT,
        DATE,
        POP_UP,
        STAR_RATING,
        THUMBNAIL
    }

    public enum ItemType {
        TITLE,
        AUTHOR,
        THUMBNAIL,
        GENRE,
        BORROWING_STATUS,
        BORROWED_BY,
        BORROWING_DATE,
        READING_STATUS,
        RATING_EMOTIONAL_IMPACT,
        RATING_CHARACTERS,
        RATING_PACING,
        RATING_STORYLINE,
        RATING_WRITING_STYLE,
        OVERALL_RATING
    }
}
