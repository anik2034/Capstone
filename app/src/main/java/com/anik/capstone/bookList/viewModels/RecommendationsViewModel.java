package com.anik.capstone.bookList.viewModels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anik.capstone.bookList.BookListItem;
import com.anik.capstone.bookList.BookListItemCreator;
import com.anik.capstone.db.BookRepository;
import com.anik.capstone.db.UserRepository;
import com.anik.capstone.model.BookModel;
import com.anik.capstone.model.BookModelCreator;
import com.anik.capstone.model.ListType;
import com.anik.capstone.network.RetrofitClient;
import com.anik.capstone.network.responses.BookResponse;
import com.anik.capstone.rec.RecommendationEngine;
import com.anik.capstone.util.ResourceHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class RecommendationsViewModel extends BookListViewModel {
    private RetrofitClient retrofitClient;
    private final BookModelCreator bookModelCreator;
    private BookModel bookModel;
    private UserRepository userRepository;
    private final MutableLiveData<Void> _onShowChooseListType = new MutableLiveData<>();
    public LiveData<Void> onShowChooseListType = _onShowChooseListType;

    @Inject
    public RecommendationsViewModel(
            ResourceHelper resourceHelper,
            BookRepository bookRepository,
            RetrofitClient retrofitClient,
            BookListItemCreator bookListItemCreator,
            BookModelCreator bookModelCreator,
            UserRepository userRepository) {
        super(resourceHelper, bookRepository, bookListItemCreator);
        this.retrofitClient = retrofitClient;
        this.bookModelCreator = bookModelCreator;
        this.userRepository = userRepository;
    }

    @Override
    public void loadBooks() {
        List<BookModel> books = bookRepository.getBooksByListType(ListType.LIBRARY);
        RecommendationEngine recommendationEngine = new RecommendationEngine(books);
        String query = recommendationEngine.selectAuthorsAndGenres();
        search(query, books);

    }

    @Override
    public void onItemClick(BookListItem bookListItem) {
        bookModel = new BookModel();
        bookModel.setAuthor(bookListItem.getAuthor());
        bookModel.setTitle(bookListItem.getTitle());
        bookModel.setCoverUrl(bookListItem.getCoverUrl());
        bookModel.setGenres(bookListItem.getGenres());
        _onShowChooseListType.setValue(null);

    }

    public void onSave(ListType listType) {
        bookModel.setListType(listType);
        bookModel.setOwnerId(userRepository.getUser().getId());
        bookRepository.insertBook(bookModel);
    }

    private void search(String query, List<BookModel> books) {
        _isProgressBarVisible.setValue(true);
        Call<BookResponse> call = retrofitClient.bookService.search(query, "rating", "eng");

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookResponse> call, @NonNull Response<BookResponse> response) {
                _isProgressBarVisible.setValue(false);
                BookResponse bookResponse = response.body();
                if (bookResponse != null && bookResponse.getNumFound() > 0) {
                    List<BookModel> searchedBooks = bookModelCreator.convertToBookList(bookResponse, books);
                    List<BookListItem> bookListItems = bookListItemCreator.convert(searchedBooks);
                    setBooks(bookListItems);
                }

            }
            @Override
            public void onFailure(@NonNull Call<BookResponse> call, @NonNull Throwable t) {
                Log.i("API CALL FAILURE", "RecommendationsViewModel line 65 error");
            }
        });
    }
}
