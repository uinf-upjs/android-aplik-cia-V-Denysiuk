package com.example.booktracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.booktracker.R
import com.example.booktracker.Screen
import com.example.booktracker.data.local.book.BookEntity
import com.example.booktracker.ui.theme.*
import com.example.booktracker.viewmodel.book.BookViewModel

@Composable
fun BookListScreen(navController: NavController, viewModel: BookViewModel) {
    val books by viewModel.books.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredBooks = books.filter { book ->
        book.title.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddBook.route) }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_book))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(AppPadding.medium)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(stringResource(R.string.search_hint))  },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppPadding.medium))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(AppPadding.small)
            ) {
                items(filteredBooks) { book ->
                    BookCard(
                        book,
                        onCardClick = {selectedBook ->
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

@Composable
fun BookCard(
    book: BookEntity,
    onEditClick: (BookEntity) -> Unit,
    onDeleteClick: (BookEntity) -> Unit,
    onCardClick: (BookEntity) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick(book) },
        elevation = CardDefaults.cardElevation(AppSize.cardElevation)
    ) {
        Column(
            modifier = Modifier.padding(AppPadding.medium)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${stringResource(R.string.author_label)}: ${book.author}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${stringResource(R.string.country_label)}: ${book.country}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${stringResource(R.string.year_label)}: ${book.year}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(AppPadding.small))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onEditClick(book) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.update),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(stringResource(R.string.delete_dialog_title)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeleteClick(book)
                                showDialog = false
                            }
                        ) {
                            Text(stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text(stringResource(R.string.no))
                        }
                    }
                )
            }
        }
    }
}


