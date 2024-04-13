package com.anik.capstone.model;

import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.rating.RatingModel;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class BookModel implements Serializable {
    private String ISBN;
    private String coverUrl;
    private String author;
    private String title;
    private String genre;
    private ReadingStatus readingStatus;
    private BorrowingModel borrowing;
    private RatingModel rating;


    public BookModel(String ISBN, String coverUrl, String author, String title, String genre, ReadingStatus readingStatus, BorrowingModel borrowing, RatingModel rating) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.readingStatus = readingStatus;
        this.borrowing = borrowing;
        this.rating = rating;
    }

    public BookModel(String ISBN, String coverUrl, String author, String title, String genre, ReadingStatus readingStatus) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.readingStatus = readingStatus;
    }

    public BookModel(String ISBN, String coverUrl, String author, String title, String genre) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genre = genre;
    }

    public BookModel(String ISBN, String coverUrl, String author, String title, String genre, ReadingStatus readingStatus, BorrowingModel borrowing) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.readingStatus = readingStatus;
        this.borrowing = borrowing;
        this.rating = new RatingModel();
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

    public BorrowingModel getBorrowingModel() {
        return borrowing;
    }

    public void setBorrowingModel(BorrowingModel borrowing) {
        this.borrowing = borrowing;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public RatingModel getRating() {
        return rating;
    }

    public void setRating(RatingModel rating) {
        this.rating = rating;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookModel bookModel = (BookModel) o;
        return Objects.equals(ISBN, bookModel.ISBN) && Objects.equals(coverUrl, bookModel.coverUrl) && Objects.equals(author, bookModel.author) && Objects.equals(title, bookModel.title) && Objects.equals(genre, bookModel.genre) && readingStatus == bookModel.readingStatus && borrowing == bookModel.borrowing;
    }
}
