package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                    BookCard(book)
                }
            }
        }
    }
}

@Composable
fun BookCard(book: BookEntity) {
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
                style = MaterialTheme.typography.bodySmall)
        }
    }
}


