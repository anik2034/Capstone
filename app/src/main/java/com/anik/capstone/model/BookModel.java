package com.anik.capstone.model;

import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.rating.RatingModel;

import java.io.Serializable;
import java.util.List;

public class BookModel implements Serializable {
    private String ISBN;
    private String coverUrl;
    private String author;
    private String title;
    private List<String> genres;
    private ReadingStatus readingStatus;
    private BorrowingModel borrowing;
    private RatingModel rating;

    public BookModel(String ISBN, String coverUrl, String author, String title, List<String> genres, ReadingStatus readingStatus, BorrowingModel borrowing, RatingModel rating) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genres = genres;
        this.readingStatus = readingStatus;
        this.borrowing = borrowing;
        this.rating = rating;
    }

    public BookModel(String ISBN, String coverUrl, String author, String title, List<String> genres) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genres = genres;
    }

    public BookModel(String ISBN, String coverUrl, String author, String title, List<String> genres, ReadingStatus readingStatus, BorrowingModel borrowing) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genres = genres;
        this.readingStatus = readingStatus;
        this.borrowing = borrowing;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public ReadingStatus getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(ReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }

    public BorrowingModel getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(BorrowingModel borrowing) {
        this.borrowing = borrowing;
    }

    public RatingModel getRating() {
        return rating;
    }

    public void setRating(RatingModel rating) {
        this.rating = rating;
    }
}
