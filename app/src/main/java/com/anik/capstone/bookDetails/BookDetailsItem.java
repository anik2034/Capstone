package com.anik.capstone.bookDetails;

import java.util.List;
import java.util.Objects;

public class BookDetailsItem {
    private String title;
    private String value;
    private String selectedValue;
    private float rating;
    private String date;
    private boolean isCenter;
    private ViewType viewType;
    private ItemType itemType;
    private String thumbnailUrl;
    private boolean isEditable;
    private List<String> singleSelection;
    private int bookModelId;

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

    public ViewType getItemViewType() {
        return viewType;
    }

    public void setItemViewType(ViewType viewType) {
        this.viewType = viewType;
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

    public boolean isCenter() {
        return isCenter;
    }

    public void setCenter(boolean center) {
        isCenter = center;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDetailsItem that = (BookDetailsItem) o;
        return Float.compare(rating, that.rating) == 0 && isCenter == that.isCenter && isEditable == that.isEditable && bookModelId == that.bookModelId && Objects.equals(title, that.title) && Objects.equals(value, that.value) && Objects.equals(selectedValue, that.selectedValue) && Objects.equals(date, that.date) && viewType == that.viewType && itemType == that.itemType && Objects.equals(thumbnailUrl, that.thumbnailUrl) && Objects.equals(singleSelection, that.singleSelection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, value, selectedValue, rating, date, isCenter, viewType, itemType, thumbnailUrl, isEditable, singleSelection, bookModelId);
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
        THUMBNAIL
    }
}
