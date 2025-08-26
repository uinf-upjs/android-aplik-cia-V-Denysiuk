package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.booktracker.R
import com.example.booktracker.ui.screens.navigation.Screen
import com.example.booktracker.ui.screens.components.BookCard
import com.example.booktracker.ui.theme.*
import com.example.booktracker.viewmodel.book.BookViewModel

@Composable
fun BookListScreen(navController: NavController, viewModel: BookViewModel) {
    // nacitavanie
    val books by viewModel.books.collectAsState()
    //hladanie knih
    var searchQuery by remember { mutableStateOf("") }

    val filteredBooks = books.filter { book ->
        book.title.contains(searchQuery, ignoreCase = true)
    }
    //struktura obrazovky
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(AppPadding.medium)
        ) {
            //field na hladanie khihy
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(stringResource(R.string.search_hint)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppPadding.medium))

            if (filteredBooks.isEmpty() && searchQuery.isNotBlank()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppPadding.medium),
                    verticalArrangement = Arrangement.spacedBy(AppPadding.small)
                ) {
                    Text(
                        text = stringResource(R.string.book_not_found),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(
                        onClick = { navController.navigate(Screen.AddBook.route) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.add_book))
                    }
                }
            } else {
                //zoznam knih s databazy
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(AppPadding.small)
                ) {
                    items(filteredBooks) { book ->
                        BookCard(
                            book,
                            onCardClick = { selectedBook ->
                                navController.navigate(Screen.BookDetail.createRoute(selectedBook.id))
                            },
                            onEditClick = { selectedBook ->
                                navController.navigate(Screen.EditBook.createRoute(selectedBook.id))

                            },
                            onDeleteClick = { selectedBook ->
                                viewModel.deleteBookById(selectedBook.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

