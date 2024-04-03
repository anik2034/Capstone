package com.anik.capstone.model;

import androidx.annotation.NonNull;

import com.anik.capstone.R;

import java.util.ArrayList;
import java.util.List;

public enum ReadingStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    PAUSED("Paused"),
    FINISHED("Finished");

    private final String displayName;

    ReadingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<String> getAllDisplayNames() {
        List<String> displayNames = new ArrayList<>();
        for (ReadingStatus status : values()) {
            displayNames.add(status.displayName);
        }
        return displayNames;
    }
}