package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.model.Article
import com.example.newsapp.util.DateUtils

/** Blue uppercase source label + "• X hours ago", as in the design. */
@Composable
fun CategoryTime(
    article: Article,
    modifier: Modifier = Modifier,
    categoryColor: Color = MaterialTheme.colorScheme.primary
) {
    val category = article.source?.name?.takeIf { it.isNotBlank() }?.uppercase() ?: "NEWS"
    val time = DateUtils.timeAgo(article.publishedAt)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelSmall,
            color = categoryColor,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        if (time.isNotBlank()) {
            Text(
                text = "•  $time",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
