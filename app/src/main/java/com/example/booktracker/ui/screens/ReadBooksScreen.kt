package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.booktracker.R
import com.example.booktracker.ui.screens.navigation.Screen
import com.example.booktracker.ui.screens.components.BookCard
import com.example.booktracker.ui.theme.AppPadding
import com.example.booktracker.viewmodel.book.BookViewModel
import com.example.booktracker.viewmodel.review.ReviewViewModel

@Composable
fun ReadBooksScreen(
    navController: NavController,
    bookViewModel: BookViewModel,
    reviewViewModel: ReviewViewModel
) {
    val books by bookViewModel.books.collectAsState()
    val reviews by reviewViewModel.allReviews.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }

    val readBooks = books.filter { book ->
        val review = reviews.find { it.bookId == book.id }
        review?.isRead == true
    }.filter { it.title.contains(searchQuery, ignoreCase = true) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(AppPadding.medium)
    ) {
        Spacer(modifier = Modifier.height(AppPadding.large))
        Spacer(modifier = Modifier.height(AppPadding.large))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(R.string.search_hint)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(AppPadding.medium))

        if (readBooks.isEmpty()) {
            Text(stringResource(R.string.no_read_books), style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(AppPadding.small)
            ) {
                items(readBooks) { book ->
                    BookCard(
                        book,
                        onCardClick = { navController.navigate(Screen.BookDetail.createRoute(it.id)) },
                        onEditClick = { navController.navigate(Screen.EditBook.createRoute(it.id)) },
                        onDeleteClick = { bookViewModel.deleteBookById(it.id) }
                    )
                }
            }
        }
    }
}
