package com.anik.capstone.network;

import com.anik.capstone.network.responses.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    @GET("search.json")
    Call<BookResponse> search(@Query("isbn") String query);
}
