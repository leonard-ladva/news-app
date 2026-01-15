package com.example.newsapp

import android.os.Bundle
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

val viewModel = NewsViewModel()

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "news"
                ) {
                    composable("news") {
                        NewsScreen(
                            navController = navController,
                            viewModel = viewModel,
                        )
                    }

                    composable(
                        "article/{articleUrl}",
                        arguments = listOf(navArgument("articleUrl") {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        // this encoding solution was made with AI
                        val encodedUrl = backStackEntry.arguments?.getString("articleUrl") ?: ""
                        val articleUrl = java.net.URLDecoder.decode(encodedUrl, "UTF-8")
                        ArticleScreen(
                            articleUrl = articleUrl,
                            navController = navController,
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }
}