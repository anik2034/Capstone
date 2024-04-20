package com.anik.capstone.model;

import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.rating.RatingModel;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public BookModel(String ISBN, String coverUrl, String author, String title, List<String> genres, ListType listType) {
        this.ISBN = ISBN;
        this.coverUrl = coverUrl;
        this.author = author;
        this.title = title;
        this.genres = genres;
        this.listType = listType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookModel bookModel = (BookModel) o;
        return id == bookModel.id && Objects.equals(ISBN, bookModel.ISBN) && Objects.equals(coverUrl, bookModel.coverUrl) && Objects.equals(author, bookModel.author) && Objects.equals(title, bookModel.title) && Objects.equals(genres, bookModel.genres) && readingStatus == bookModel.readingStatus && Objects.equals(borrowing, bookModel.borrowing) && Objects.equals(rating, bookModel.rating) && listType == bookModel.listType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ISBN, coverUrl, author, title, genres, readingStatus, borrowing, rating, listType);
    }
}
