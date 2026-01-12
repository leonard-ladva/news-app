package com.example.newsapp.data.remote

import com.example.newsapp.data.model.ApiResponse

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 21,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String,
    ) : ApiResponse
}