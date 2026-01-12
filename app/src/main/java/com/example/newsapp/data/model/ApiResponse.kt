package com.example.newsapp.data.model

data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>,
    val code: String?, // Only populated on error
    val message: String? // Only populated on error
)
