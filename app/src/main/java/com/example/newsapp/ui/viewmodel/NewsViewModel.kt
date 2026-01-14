package com.example.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>, val isPaging: Boolean = false) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {
    private val allArticles = mutableListOf<Article>()
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState


    private var currentPage = 1
    private var isLoading = false
    private var isPaging = false
    private var canLoadMore = true

    init {
        loadFirstPage()
    }

    fun loadFirstPage() {
        currentPage = 1
        canLoadMore = true
        loadPage(1)
    }

    fun loadNextPage() {
        if (isLoading || !canLoadMore) return
        loadPage(currentPage + 1)

    }

    private fun loadPage(page: Int) {
        isLoading = true
        isPaging = true

        viewModelScope.launch {
            try {
                if (page == 1) {
                    _uiState.value = NewsUiState.Loading
                }

                val response = repository.getTopHeadlines(page =page)
                val newArticles = response.articles

                if (newArticles.isEmpty()) {
                   canLoadMore = false
                } else {
                    allArticles.addAll(newArticles)
                    _uiState.value = NewsUiState.Success(allArticles.toList())
                    currentPage = page
                }
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.message ?: "Unknown error")
            } finally {
                isLoading = false
                isPaging = false
            }
        }
    }

}