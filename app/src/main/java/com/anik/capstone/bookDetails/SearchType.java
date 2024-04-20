package com.anik.capstone.bookDetails;

import com.anik.capstone.model.ReadingStatus;

import java.util.ArrayList;
import java.util.List;

public enum SearchType {
    SEARCH_BY_ISBN("ARG_SEARCH_ISBN"),
    SEARCH_BY_TITLE("ARG_SEARCH_TITLE");
    private final String displayName;

    SearchType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<String> getAllDisplayNames() {
        List<String> displayNames = new ArrayList<>();
        for (SearchType status : values()) {
            displayNames.add(status.displayName);
        }
        return displayNames;
    }
    public static SearchType getReadingStatus(String str){
        SearchType searchType  = null;
        if(str.equals(SEARCH_BY_ISBN.displayName)) searchType = SEARCH_BY_ISBN;
        else if(str.equals(SEARCH_BY_TITLE.displayName)) searchType = SEARCH_BY_TITLE;

        return searchType;
    }
}
