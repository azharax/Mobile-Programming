package com.example.loginmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loginmvvm.data.local.database.AppDatabase
import com.example.loginmvvm.data.repository.UserRepository
import com.example.loginmvvm.ui.screen.LoginScreen
import com.example.loginmvvm.viewmodel.LoginViewModel
import com.example.loginmvvm.viewmodel.LoginViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database =
            AppDatabase.getDatabase(this)
        val repository =
            UserRepository(
                database.userDao()
            )
        val factory =
            LoginViewModelFactory(repository)
        setContent {
            val viewModel: LoginViewModel =
                viewModel(factory = factory)
            androidx.compose.runtime.LaunchedEffect(Unit) {
                viewModel.insertDummyUser()
            }
            LoginScreen(viewModel)
        }
    }
}