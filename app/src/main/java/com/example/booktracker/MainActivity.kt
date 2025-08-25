package com.example.booktracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booktracker.ui.screens.BookListScreen
import com.example.booktracker.ui.screens.AddBookScreen
import com.example.booktracker.ui.theme.BookTrackerTheme
import com.example.booktracker.viewmodel.BookViewModel
import com.example.booktracker.viewmodel.BookViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as BookApplication
        val factory = BookViewModelFactory(app.repository)
        val viewModel = ViewModelProvider(this, factory)[BookViewModel::class.java]
        setContent {
            BookTrackerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.BookList.route
                ) {
                    composable(Screen.BookList.route) {
                        BookListScreen(navController, viewModel)
                    }
                    composable(Screen.AddBook.route) {
                        AddBookScreen(navController)
                    }
                }
            }
        }
    }
}
