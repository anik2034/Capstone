package com.anik.capstone.model;

import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.borrowing.BorrowingStatus;
import com.anik.capstone.model.rating.RatingModel;
import com.anik.capstone.network.responses.BookResponse;
import com.anik.capstone.network.responses.DocumentResponse;
import com.anik.capstone.util.GenreHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class BookModelCreator {

    @Inject
    public BookModelCreator() {}

    public BookModel convertToBook(BookResponse bookResponse, UserModel user) {
        DocumentResponse documentResponse = bookResponse.getDocumentList().get(0);
        String title = documentResponse.getTitle();

        String isbn = documentResponse.getIsbnList() == null ? "" : documentResponse.getIsbnList().get(0);

        String author = documentResponse.getAuthorList() == null ? "" : documentResponse.getAuthorList().get(0);
        int coverId = documentResponse.getCoverId();
        List<String> genres = GenreHelper.getGenres(documentResponse.getSubjectList());

        String coverUrl = coverId == 0 ? "" : "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";
        return new BookModel(isbn, coverUrl, author, title, genres, ReadingStatus.NOT_STARTED,
                new BorrowingModel(BorrowingStatus.NOT_BORROWED, "", ""),
                new RatingModel(), ListType.LIBRARY, user.getId());
    }


    public List<BookModel> convertToBookList(BookResponse bookResponse, List<BookModel> books) {
        Set<BookModel> booksSet = new HashSet<>(books);
        List<BookModel> newBooks = new ArrayList<>();
        List<DocumentResponse> documentResponses = bookResponse.getDocumentList();
        for (int i = 0; i < documentResponses.size(); i++) {
            DocumentResponse documentResponse = documentResponses.get(i);

            if (documentResponse == null || documentResponse.getIsbnList() == null || documentResponse.getIsbnList().isEmpty() ||
                    documentResponse.getAuthorList() == null || documentResponse.getAuthorList().isEmpty() ||
                    documentResponse.getTitle() == null) {
                continue;
            }

            String title = documentResponse.getTitle();
            String isbn = documentResponse.getIsbnList().get(0);
            String author = documentResponse.getAuthorList().get(0);
            int coverId = documentResponse.getCoverId();
            List<String> genres = GenreHelper.getGenres(documentResponse.getSubjectList());

            if (genres == null) {
                continue;
            }


            BookModel potentialNewBook = new BookModel(isbn, "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg",
                    author, title, genres, ReadingStatus.NOT_STARTED, new BorrowingModel(BorrowingStatus.NOT_BORROWED, "", ""),
                    new RatingModel(), ListType.LIBRARY, "");

            if (!booksSet.contains(potentialNewBook)) {
                newBooks.add(potentialNewBook);
            }
        }
        return newBooks;
    }


}
