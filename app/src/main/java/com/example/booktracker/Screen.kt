package com.example.booktracker

//routes(list of all srceens in app) pre navigaciu
sealed class Screen(val route: String) {
    object BookList : Screen("book_list")
    object AddBook : Screen("add_book")
    object EditBook : Screen("edit_book/{bookId}") {
        fun createRoute(bookId: Long) = "edit_book/$bookId"
    }

}
