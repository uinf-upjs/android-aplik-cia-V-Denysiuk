package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.NavController
import com.example.booktracker.Screen
import com.example.booktracker.data.local.BookEntity
import com.example.booktracker.ui.theme.*
import com.example.booktracker.viewmodel.BookViewModel

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
                Icon(Icons.Default.Add, contentDescription = "Add Book")
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
                label = { Text("Search by title") },
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
    onDeleteClick: (BookEntity) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                text = "Author: ${book.author}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Country: ${book.country}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Year: ${book.year}",
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
                        contentDescription = "Edit Book",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Book",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Are you sure?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onDeleteClick(book)
                                showDialog = false
                            }
                        ) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}


