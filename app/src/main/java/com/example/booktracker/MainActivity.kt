package com.example.booktracker

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.booktracker.ui.screens.*
import com.example.booktracker.ui.screens.navigation.BottomNavItem
import com.example.booktracker.ui.screens.navigation.Screen
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
                //navigacia
                val navController = rememberNavController()
                val bottomNavItems = listOf(
                    BottomNavItem.Books,
                    BottomNavItem.Read,
                    BottomNavItem.Add,
                    BottomNavItem.Profile
                )
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val currentRoute =
                                navController.currentBackStackEntryAsState().value?.destination?.route
                            bottomNavItems.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = item.title) },
                                    label = { Text(item.title) },
                                    selected = currentRoute == item.route,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.BookList.route,
                       modifier = Modifier.padding(paddingValues)
                    ) {
                        //bottom navigacia
                        composable(BottomNavItem.Books.route) {
                            BookListScreen(navController, bookViewModel)
                        }
                        composable(BottomNavItem.Read.route) {
                            ReadBooksScreen(navController, bookViewModel, reviewViewModel)
                        }
                        composable(BottomNavItem.Add.route) {
                            AddBookScreen(navController, bookViewModel)
                        }
                        composable(BottomNavItem.Profile.route) {
                            ProfileScreen(bookViewModel, reviewViewModel)
                        }


                        //navigacia
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
}


