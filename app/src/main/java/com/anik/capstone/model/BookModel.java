package com.anik.capstone.model;

public class BookModel {
    private String coverUrl;
    private String author;
    private String title;
    private String genre;
    private ReadingStatus readingStatus;
    private BorrowingStatus borrowingStatus;

    public BookModel(String coverUrl, String author, String title, String genre, ReadingStatus readingStatus, BorrowingStatus borrowingStatus) {
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
}
