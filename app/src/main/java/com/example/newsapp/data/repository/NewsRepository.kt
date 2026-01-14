package com.example.newsapp.data.repository

import com.example.newsapp.data.model.ApiResponse
import com.example.newsapp.data.remote.RetrofitInstance


const val DEFAULT_COUNTRY = "us"
const val DEFAULT_PAGE_SIZE = 21

class NewsRepository {
    suspend fun getTopHeadlines(
        country: String = DEFAULT_COUNTRY,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        page: Int = 1
    ): ApiResponse {
        return RetrofitInstance.api.getTopHeadlines(
            country = country, pageSize, page)
    }
}