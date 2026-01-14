package com.example.newsapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.components.ArticleCard
import com.example.newsapp.ui.viewmodel.NewsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.viewmodel.NewsUiState

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
//    val articles by viewModel.articles.collectAsState()
    val uiState by viewModel.uiState.collectAsState()


    val gridState = rememberLazyGridState()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val columns = if (isLandscape) 3 else 2

    when (uiState) {
        is NewsUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NewsUiState.Error -> {
           val message = (uiState as NewsUiState.Error).message
            ErrorScreen(message) { viewModel.loadFirstPage() }
        }
        is NewsUiState.Success -> {
            val articles = (uiState as NewsUiState.Success).articles
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(columns),
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(articles) { article ->
                    ArticleCard(article)
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    if ((uiState as NewsUiState.Success).isPaging) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

            }
        }
    }

    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastVisible >= totalItems - 6
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            viewModel.loadNextPage()
        }
    }

}
