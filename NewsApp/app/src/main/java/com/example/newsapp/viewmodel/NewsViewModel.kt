package com.example.newsapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.local.toArticle
import com.example.newsapp.data.local.toSavedArticle
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsRepository()
    private val savedDao = NewsDatabase.getInstance(application).savedArticleDao()

    // ---- Home (top headlines) ----
    private val _homeState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val homeState: StateFlow<NewsUiState> = _homeState.asStateFlow()

    // ---- Search ----
    private val _searchState = MutableStateFlow<NewsUiState>(NewsUiState.Idle)
    val searchState: StateFlow<NewsUiState> = _searchState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    // ---- Saved (Room) ----
    val savedArticles: StateFlow<List<Article>> =
        savedDao.getAll()
            .map { list -> list.map { it.toArticle() } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Article currently opened in the detail screen.
    var selectedArticle: Article? by mutableStateOf(null)
        private set

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _homeState.value = NewsUiState.Loading
            try {
                val response = repository.getNews()
                _homeState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _homeState.value = NewsUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun onQueryChange(query: String) {
        searchQuery = query
    }

    fun search() {
        val query = searchQuery.trim()
        if (query.isEmpty()) {
            _searchState.value = NewsUiState.Idle
            return
        }
        viewModelScope.launch {
            _searchState.value = NewsUiState.Loading
            try {
                val response = repository.searchNews(query)
                _searchState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _searchState.value = NewsUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun selectArticle(article: Article) {
        selectedArticle = article
    }

    fun isSaved(url: String): Boolean = savedArticles.value.any { it.url == url }

    fun toggleSave(article: Article) {
        viewModelScope.launch {
            if (savedDao.exists(article.url)) {
                savedDao.deleteByUrl(article.url)
            } else {
                savedDao.insert(article.toSavedArticle())
            }
        }
    }
}
