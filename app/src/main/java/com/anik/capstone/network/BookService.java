package com.anik.capstone.network;

import com.anik.capstone.network.responses.BookResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface BookService {
    @GET("search.json")
    Call<BookResponse> searchByISBN(@Query("isbn") String query);
    @GET("search.json")
    Call<BookResponse> searchByTitle(@Query("title")String query);

    @GET("search.json")
    Call<BookResponse> search(@Query("q")  String options, @Query("sort") String sort, @Query("language") String language);
    
}
