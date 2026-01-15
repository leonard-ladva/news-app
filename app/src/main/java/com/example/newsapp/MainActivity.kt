package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.ui.screens.ArticleScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.screens.NewsScreen
import com.example.newsapp.ui.viewmodel.NewsViewModel

sealed class Screen(val route: String) {
    object NewsList : Screen("news")
    object ArticleScreen : Screen("article/{articleUrl}")
}

val viewModel = NewsViewModel()

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController() // <-- create it here

                NavHost(
                    navController = navController,
                    startDestination = Screen.NewsList.route
                ) {
                    composable(Screen.NewsList.route) {
                        NewsScreen(navController = navController, viewModel = viewModel)
                    }

                    composable(
                        route = Screen.ArticleScreen.route,
                        arguments = listOf(navArgument("articleUrl") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val encodedUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""
                        val articleUrl = java.net.URLDecoder.decode(encodedUrl, "UTF-8")
                        Log.d("MainActivity", "Article URL: $articleUrl")
                        ArticleScreen(
                            articleUrl = articleUrl,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
//                Scaffold(
//                    topBar = {
//                        TopAppBar(
//                            title = { Text("News App") }
//                        )
//                    }
//                ) {padding ->
//                    NewsScreen(modifier = Modifier.padding(padding))
//                }
            }
        }
    }
}