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
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private var currentPage = 1
    private var isLoading = false
    private var canLoadMore = true

    init {
        loadFirstPage()
    }

    fun loadFirstPage() {
        currentPage = 1
        canLoadMore = true
        loadPage(1, reset = true)
    }

    fun loadNextPage() {
        if (isLoading || !canLoadMore) return
        loadPage(currentPage + 1, reset = false)

    }

    private fun loadPage(page: Int, reset: Boolean) {
        isLoading = true

        viewModelScope.launch {
            try {
                val response = repository.getTopHeadlines(page =page)
                val newArticles = response.articles

               if (newArticles.isEmpty()) {
                   canLoadMore = false
                   return@launch
               }

                if (reset) {
                    _articles.value = newArticles
                } else {
                    _articles.value = _articles.value + newArticles
                }

                currentPage = page
            } finally {
                isLoading = false
            }
        }
    }

}