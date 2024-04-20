package com.anik.capstone.model.borrowing;

import java.util.ArrayList;
import java.util.List;

public enum BorrowingStatus {
    BORROWED("Borrowed"),
    NOT_BORROWED("Not Borrowed");

    private final String displayName;

    BorrowingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<String> getAllDisplayNames() {
        List<String> displayNames = new ArrayList<>();
        for (BorrowingStatus status : values()) {
            displayNames.add(status.displayName);
        }
        return displayNames;
    }

    public static BorrowingStatus getBorrowingStatus(String str){
        BorrowingStatus borrowingStatus = null;
       if(str.equals(BORROWED.displayName)) borrowingStatus = BORROWED;
       else if(str.equals(NOT_BORROWED.displayName)) borrowingStatus = NOT_BORROWED;
       return borrowingStatus;
    }
}