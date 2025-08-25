package com.example.booktracker

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.booktracker.ui.screens.*
import com.example.booktracker.ui.theme.BookTrackerTheme
import com.example.booktracker.viewmodel.*

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
                        AddBookScreen(navController, viewModel)
                    }
                    composable(
                        Screen.EditBook.route,
                        listOf(navArgument("bookId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
                        EditBookScreen(navController, viewModel, bookId)
                    }
                }
            }
        }
    }
}

