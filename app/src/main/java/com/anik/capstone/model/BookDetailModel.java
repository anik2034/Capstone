package com.anik.capstone.model;

public class BookDetailModel {

    private String title;
    private String value;
    private String date;
    private ItemViewType itemViewType;
    private String thumbnailUrl;
    private boolean isEditable;

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

    public enum ItemViewType {
        HEADER,
        EDITABLE_TEXT,
        DATE,
        SPINNER,
        STAR_RATING,
        THUMBNAIL
    }
}
