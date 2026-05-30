package com.example.studentapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentapp.data.SiswaDao

class StudentViewModelFactory(
    private val dao: SiswaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudentViewModel(dao) as T
    }
}