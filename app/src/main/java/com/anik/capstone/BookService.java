package com.anik.capstone;

import com.anik.capstone.model.BookModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    @GET("search.json")
    Call<BookResponse> search(@Query("isbn") String query);
}
