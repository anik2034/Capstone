package com.anik.capstone.newBook;

import com.anik.capstone.bookDetails.SearchType;

public class BookDetailsData {
    public final SearchType searchType;
    public final String searchValue;

    public BookDetailsData(SearchType searchType, String searchValue) {
        this.searchType = searchType;
        this.searchValue = searchValue;
    }
}
