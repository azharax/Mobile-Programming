package com.example.studentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentapp.data.AppDatabase
import com.example.studentapp.ui.MainScreen
import com.example.studentapp.viewmodel.StudentViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase
            .getDatabase(applicationContext)
            .siswaDao()

        setContent {

            val viewModel = StudentViewModel(dao)

            MainScreen(viewModel)
        }
    }
}