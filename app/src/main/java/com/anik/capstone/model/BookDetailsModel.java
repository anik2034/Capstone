package com.anik.capstone.model;

import java.util.List;
import java.util.Objects;

public class BookDetailsModel {
    private String title;
    private String value;
    private String selectedValue;
    private float rating;
    private String date;
    private ItemViewType itemViewType;
    private String thumbnailUrl;
    private boolean isEditable;
    private List<String> singleSelection;

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

    public ItemViewType getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(ItemViewType itemViewType) {
        this.itemViewType = itemViewType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDetailsModel that = (BookDetailsModel) o;
        return Float.compare(rating, that.rating) == 0 && isEditable == that.isEditable && Objects.equals(title, that.title) && Objects.equals(value, that.value) && Objects.equals(selectedValue, that.selectedValue) && Objects.equals(date, that.date) && itemViewType == that.itemViewType && Objects.equals(thumbnailUrl, that.thumbnailUrl) && Objects.equals(singleSelection, that.singleSelection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, value, selectedValue, rating, date, itemViewType, thumbnailUrl, isEditable, singleSelection);
    }

    public enum ItemViewType {
        HEADER,
        EDITABLE_TEXT,
        DATE,
        SPINNER,
        STAR_RATING,
        THUMBNAIL
    }
}
