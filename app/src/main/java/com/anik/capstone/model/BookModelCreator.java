package com.anik.capstone.model;

import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.borrowing.BorrowingStatus;
import com.anik.capstone.model.rating.RatingModel;
import com.anik.capstone.network.responses.BookResponse;
import com.anik.capstone.network.responses.DocumentResponse;
import com.anik.capstone.util.GenreHelper;

import java.util.List;

import javax.inject.Inject;

public class BookModelCreator {

    @Inject
    public BookModelCreator() {}

    public BookModel convertToBook(BookResponse bookResponse, UserModel user) {
        DocumentResponse documentResponse = bookResponse.getDocumentList().get(0);
        String title = documentResponse.getTitle();
        String isbn = documentResponse.getIsbnList().get(0);
        String author = documentResponse.getAuthorList().get(0);
        int coverId = documentResponse.getCoverId();
        List<String> genres = GenreHelper.getGenres(documentResponse.getSubjectList());
        return new BookModel(isbn, "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg",
                author, title, genres,ReadingStatus.NOT_STARTED, new BorrowingModel(BorrowingStatus.NOT_BORROWED, "",""), new RatingModel(), ListType.LIBRARY, user.getId());
    }
}
