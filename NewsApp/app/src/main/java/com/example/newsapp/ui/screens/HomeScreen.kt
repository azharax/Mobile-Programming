package com.example.newsapp.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.model.Article
import com.example.newsapp.ui.components.EmptyView
import com.example.newsapp.ui.components.ErrorView
import com.example.newsapp.ui.components.FeaturedNewsCard
import com.example.newsapp.ui.components.LoadingView
import com.example.newsapp.ui.components.NewsListItem
import com.example.newsapp.viewmodel.NewsUiState

@Composable
fun HomeScreen(
    state: NewsUiState,
    onArticleClick: (Article) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is NewsUiState.Loading -> LoadingView(modifier)
        is NewsUiState.Error -> ErrorView(state.message, onRetry, modifier)
        is NewsUiState.Idle -> {}
        is NewsUiState.Success -> {
            val articles = state.articles
            if (articles.isEmpty()) {
                EmptyView("No news available", "Please try again later.", modifier)
                return
            }
            val featured = articles.first()
            val rest = articles.drop(1)
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    FeaturedNewsCard(article = featured, onClick = { onArticleClick(featured) })
                }
                item {
                    Text(
                        text = "Breaking News",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 20.dp, bottom = 4.dp)
                    )
                }
                items(rest, key = { it.url }) { article ->
                    NewsListItem(article = article, onClick = { onArticleClick(article) })
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                }
            }
        }
    }
}
