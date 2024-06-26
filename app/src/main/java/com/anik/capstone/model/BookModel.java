package com.anik.capstone.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.borrowing.BorrowingStatus;
import com.anik.capstone.model.rating.RatingModel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "books")
public class BookModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String ISBN;
    private String coverUrl;
    private String author;
    private String title;
    private List<String> genres;
    private ReadingStatus readingStatus;
    private BorrowingModel borrowing;
    private RatingModel rating;
    private ListType listType;
    private String ownerId;

    public BookModel() {
        this.ISBN = "";
        this.coverUrl = "";
        this.author = "";
        this.title = "";
        this.genres = Arrays.asList("", "");
        this.readingStatus = ReadingStatus.NOT_STARTED;
        this.borrowing = new BorrowingModel(BorrowingStatus.NOT_BORROWED, "", "");
        this.ownerId = "";
        this.rating = new RatingModel();
        this.listType = ListType.LIBRARY;
    }

    public BookModel(String ISBN, String coverUrl, String author, String title, List<String> genres, ReadingStatus readingStatus, BorrowingModel borrowing, RatingModel rating, ListType listType, String ownerId) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genres = genres;
        this.readingStatus = readingStatus;
        this.borrowing = borrowing;
        this.rating = rating;
        this.listType = listType;
        this.ownerId = ownerId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ListType getListType() {
        return listType;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookModel bookModel = (BookModel) o;
        return Objects.equals(title.toLowerCase(), bookModel.title.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
