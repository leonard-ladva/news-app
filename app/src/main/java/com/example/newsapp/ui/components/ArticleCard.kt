package com.example.newsapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.Screen
import com.example.newsapp.data.model.Article
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ArticleCard(
    article: Article,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable( onClick = {
                val encodedUrl = URLEncoder.encode(article.url, StandardCharsets.UTF_8.toString())
                navController.navigate("article/$encodedUrl")}
            )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = article.title ?: "", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.description ?: "",
                maxLines = 7,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

