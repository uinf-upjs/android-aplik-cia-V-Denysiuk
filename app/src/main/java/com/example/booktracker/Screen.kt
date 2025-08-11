package com.example.booktracker

//routes(list of all srceens in app) pre navigaciu
sealed class Screen(val route: String) {
    object BookList : Screen("book_list")
    object AddBook : Screen("add_book")
}
