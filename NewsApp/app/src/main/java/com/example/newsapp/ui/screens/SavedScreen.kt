package com.example.newsapp.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.model.Article
import com.example.newsapp.ui.components.EmptyView
import com.example.newsapp.ui.components.NewsListItem

@Composable
fun SavedScreen(
    saved: List<Article>,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    if (saved.isEmpty()) {
        EmptyView(
            title = "No saved articles yet",
            subtitle = "Tap the bookmark on any article to save it for later.",
            modifier = modifier
        )
        return
    }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(saved, key = { it.url }) { article ->
            NewsListItem(article = article, onClick = { onArticleClick(article) })
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}
