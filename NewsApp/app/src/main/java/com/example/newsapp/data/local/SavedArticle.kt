package com.example.newsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.Source

@Entity(tableName = "saved_articles")
data class SavedArticle(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val sourceName: String?,
    val savedAt: Long = System.currentTimeMillis()
)

fun SavedArticle.toArticle(): Article = Article(
    source = Source(id = null, name = sourceName),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun Article.toSavedArticle(): SavedArticle = SavedArticle(
    url = url,
    title = title,
    description = description,
    content = content,
    author = author,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    sourceName = source?.name
)
