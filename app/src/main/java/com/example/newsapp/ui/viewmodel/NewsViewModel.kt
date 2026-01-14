package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {
    init {
        loadTopHeadlines()
    }
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles


    fun loadTopHeadlines() {
        viewModelScope.launch {
            try {
                val response = repository.getTopHeadlines()
                _articles.value = response.articles
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}