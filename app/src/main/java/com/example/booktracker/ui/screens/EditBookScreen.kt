package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.NavController
import com.example.booktracker.ui.theme.AppPadding
import com.example.booktracker.viewmodel.BookViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookScreen(
    navController: NavController,
    viewModel: BookViewModel,
    bookId: Long
) {
    val book = viewModel.getBookById(bookId).collectAsState(initial = null).value

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var pages by remember { mutableStateOf("") }

    LaunchedEffect(book) {
        book?.let {
            title = it.title
            author = it.author
            country = it.country
            year = it.year
            language = it.language
            pages = it.pages
        }
    }

    val isUpdateEnabled = title.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EDIT BOOK") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(AppPadding.medium),
            verticalArrangement = Arrangement.spacedBy(AppPadding.medium)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title *") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("Country") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = language,
                onValueChange = { language = it },
                label = { Text("Language") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = pages,
                onValueChange = { pages = it },
                label = { Text("Pages") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (book != null) {
                        val updatedBook = book.copy(
                            title = title,
                            author = author.ifBlank { "Unknown" },
                            country = country.ifBlank { "Unknown" },
                            year = year.ifBlank { "Unknown" },
                            language = language.ifBlank { "Unknown" },
                            pages = pages.ifBlank { "Unknown" }
                        )
                        viewModel.updateBook(updatedBook)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isUpdateEnabled
            ) {
                Text("Update")
            }
        }
    }
}
