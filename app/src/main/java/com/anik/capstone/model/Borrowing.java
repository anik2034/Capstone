package com.anik.capstone.model;

public class Borrowing {
    private BorrowingStatus borrowingStatus;
    private String name;
    private String date;


    public Borrowing(BorrowingStatus borrowingStatus) {
        this.borrowingStatus = borrowingStatus;
    }

    public Borrowing(BorrowingStatus borrowingStatus, String name, String date) {
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
}
