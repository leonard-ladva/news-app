package com.example.newsapp.data.remote

import com.example.newsapp.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val DEFAULT_COUNTRY = "us"
const val DEFAULT_PAGE_SIZE = 21

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = DEFAULT_COUNTRY,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("page") page: Int = 1,
    ) : ApiResponse
}