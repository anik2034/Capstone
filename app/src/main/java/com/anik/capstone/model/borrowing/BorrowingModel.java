package com.anik.capstone.model.borrowing;

import java.util.Objects;

public class BorrowingModel {
    private BorrowingStatus borrowingStatus;
    private String name;
    private String date;


    public BorrowingModel(BorrowingStatus borrowingStatus) {
        this.borrowingStatus = borrowingStatus;
    }

    public BorrowingModel(BorrowingStatus borrowingStatus, String name, String date) {
        this.borrowingStatus = borrowingStatus;
        this.name = name;
        this.date = date;
    }

    public BorrowingStatus getBorrowingStatus() {
        return borrowingStatus;
    }

    public void setBorrowingStatus(BorrowingStatus borrowingStatus) {
        this.borrowingStatus = borrowingStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowingModel that = (BorrowingModel) o;
        return borrowingStatus == that.borrowingStatus && Objects.equals(name, that.name) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(borrowingStatus, name, date);
    }
}
