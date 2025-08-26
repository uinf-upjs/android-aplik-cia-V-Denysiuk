package com.example.booktracker.ui.screens.navigation

sealed class Screen(val route: String) {
    object BookList : Screen("book_list")
    object AddBook : Screen("add_book")
    object EditBook : Screen("edit_book/{bookId}") {
        fun createRoute(bookId: Long) = "edit_book/$bookId"
    }
    object BookDetail : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: Long) = "book_detail/$bookId"
    }
    object EditReview : Screen("edit_review/{bookId}") {
        fun createRoute(bookId: Long) = "edit_review/$bookId"
    }

}
