package com.example.newsapp.data.repository

import com.example.newsapp.data.api.RetrofitClient
import com.example.newsapp.data.model.NewsResponse

class NewsRepository {

    private companion object {
        const val API_KEY = "5946f2cff50c4e2d867af66c00f9ba55"
    }

    suspend fun getNews(): NewsResponse =
        RetrofitClient.apiService.getTopHeadlines(apiKey = API_KEY)

    suspend fun searchNews(query: String): NewsResponse =
        RetrofitClient.apiService.searchNews(query = query, apiKey = API_KEY)
}
