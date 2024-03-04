package com.anik.capstone.model;

import java.util.Objects;

public class BookModel {
    private String ISBN;
    private String coverUrl;
    private String author;
    private String title;
    private String genre;
    private ReadingStatus readingStatus;
    private BorrowingStatus borrowingStatus;

    public BookModel(String ISBN, String coverUrl, String author, String title, String genre, ReadingStatus readingStatus, BorrowingStatus borrowingStatus) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.readingStatus = readingStatus;
        this.borrowingStatus = borrowingStatus;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public ReadingStatus getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(ReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }

    public BorrowingStatus getBorrowingStatus() {
        return borrowingStatus;
    }

    public void setBorrowingStatus(BorrowingStatus borrowingStatus) {
        this.borrowingStatus = borrowingStatus;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookModel bookModel = (BookModel) o;
        return Objects.equals(ISBN, bookModel.ISBN) && Objects.equals(coverUrl, bookModel.coverUrl) && Objects.equals(author, bookModel.author) && Objects.equals(title, bookModel.title) && Objects.equals(genre, bookModel.genre) && readingStatus == bookModel.readingStatus && borrowingStatus == bookModel.borrowingStatus;
    }
}
