package com.anik.capstone.network;

import com.anik.capstone.network.responses.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookService {
    @GET("search.json")
    Call<BookResponse> searchByISBN(@Query("isbn") String query);
    @GET("search.json")
    Call<BookResponse> searchByTitle(@Query("title")String query);
    
}
