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
import com.example.booktracker.viewmodel.book.*
import com.example.booktracker.viewmodel.review.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as BookApplication
        //book
        val bookViewModelFactory = BookViewModelFactory(app.bookRepository)
        val bookViewModel = ViewModelProvider(this, bookViewModelFactory)[BookViewModel::class.java]

        //review
        val reviewViewModelFactory = ReviewViewModelFactory(app.reviewRepository)
        val reviewViewModel =
            ViewModelProvider(this, reviewViewModelFactory)[ReviewViewModel::class.java]
        setContent {
            BookTrackerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.BookList.route
                ) {
                    composable(Screen.BookList.route) {
                        BookListScreen(navController, bookViewModel)
                    }
                    composable(Screen.AddBook.route) {
                        AddBookScreen(navController, bookViewModel)
                    }
                    composable(
                        Screen.EditBook.route,
                        listOf(navArgument("bookId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
                        EditBookScreen(
                            navController, bookViewModel, bookId
                        )
                    }
                    composable(
                        Screen.BookDetail.route,
                        listOf(navArgument("bookId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
                        BookDetailScreen(
                            navController, bookId, bookViewModel, reviewViewModel
                        )
                    }
                    composable(
                        Screen.EditReview.route,
                        listOf(navArgument("bookId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
                        EditReviewScreen(
                            navController, bookId, bookViewModel, reviewViewModel
                        )
                    }

                }
            }
        }
    }
}

