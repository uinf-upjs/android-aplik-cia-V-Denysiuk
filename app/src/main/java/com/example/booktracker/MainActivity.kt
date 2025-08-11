package com.example.booktracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booktracker.ui.screens.BookListScreen
import com.example.booktracker.ui.screens.AddBookScreen
import com.example.booktracker.ui.theme.BookTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookTrackerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.BookList.route
                ) {
                    composable(Screen.BookList.route) {
                        BookListScreen(navController)
                    }
                    composable(Screen.AddBook.route) {
                        AddBookScreen()
                    }
                }
            }
        }
    }
}
