package com.anik.capstone.bookList;

import java.util.List;
import java.util.Objects;

public class BookListItem {
    private int bookModelId;
    private String title;
    private String author;
    private String coverUrl;
    private List<String> genres;

    public BookListItem(int bookModelId, String title, String author, String coverUrl, List<String> genres) {
        this.bookModelId = bookModelId;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.genres = genres;
    }

    public int getBookModelId() {
        return bookModelId;
    }

    public void setBookModelId(int bookModelId) {
        this.bookModelId = bookModelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookListItem that = (BookListItem) o;
        return bookModelId == that.bookModelId && Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(coverUrl, that.coverUrl) && Objects.equals(genres, that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookModelId, title, author, coverUrl, genres);
    }
}
