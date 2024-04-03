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
}