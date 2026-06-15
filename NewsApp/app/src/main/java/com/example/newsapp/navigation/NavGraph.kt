package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.screens.DetailScreen
import com.example.newsapp.ui.screens.MainScreen
import com.example.newsapp.viewmodel.NewsViewModel

object Routes {
    const val MAIN = "main"
    const val DETAIL = "detail"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel = viewModel<NewsViewModel>()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(viewModel = viewModel) { article ->
                viewModel.selectArticle(article)
                navController.navigate(Routes.DETAIL)
            }
        }
        composable(Routes.DETAIL) {
            val article = viewModel.selectedArticle
            // Recompose when the saved list changes so the bookmark icon stays in sync.
            val saved by viewModel.savedArticles.collectAsState()
            if (article != null) {
                DetailScreen(
                    article = article,
                    isSaved = saved.any { it.url == article.url },
                    onToggleSave = { viewModel.toggleSave(article) },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
