package com.example.loginmvvm.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginmvvm.data.local.entity.User
import com.example.loginmvvm.data.repository.UserRepository
import kotlinx.coroutines.launch
class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var loginState by mutableStateOf("")
        private set
    fun login(
        username: String,
        password: String
    ) {
        viewModelScope.launch {

            val user = repository.login(
                username,
                password
            )
            loginState =
                if (user != null) {
                    "Login Berhasil"
                } else {
                    "Username atau Password Salah"
                }
        }
    }
    fun insertDummyUser() {
        viewModelScope.launch {
            repository.insert(
                User(
                    username = "admin",
                    password = "12345"
                )
            )
        }
    }
}