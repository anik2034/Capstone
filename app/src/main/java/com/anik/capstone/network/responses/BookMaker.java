package com.anik.capstone.network.responses;

import com.anik.capstone.model.BookModel;
import com.anik.capstone.util.GenreHelper;

import java.util.List;

public class BookMaker {

    public static BookModel convertToBook(BookResponse bookResponse){
        DocumentResponse documentResponse = bookResponse.getDocumentList().get(0);
        String title = documentResponse.getTitle();
        String isbn = documentResponse.getIsbnList().get(0);
        String author  = documentResponse.getAuthorList().get(0);
        int coverId = documentResponse.getCoverId();
        List<String> genres = GenreHelper.getGenres(documentResponse.getSubjectList());
        return new BookModel(isbn,"https://covers.openlibrary.org/b/id/"+coverId+"-M.jpg", author,title, genres);
    }
}
