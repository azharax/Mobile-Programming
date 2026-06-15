package com.example.newsapp.viewmodel

import com.example.newsapp.data.model.Article

sealed class NewsUiState {
    object Idle : NewsUiState()
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}
