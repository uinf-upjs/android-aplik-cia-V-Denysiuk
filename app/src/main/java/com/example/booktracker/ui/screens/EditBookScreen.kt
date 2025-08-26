package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.booktracker.R
import com.example.booktracker.ui.theme.AppPadding
import com.example.booktracker.viewmodel.book.BookViewModel


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
                title = { Text(stringResource(R.string.edit_book_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
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
                label = { Text(stringResource(R.string.title_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text(stringResource(R.string.author_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text(stringResource(R.string.country_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text(stringResource(R.string.year_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = language,
                onValueChange = { language = it },
                label = { Text(stringResource(R.string.language_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = pages,
                onValueChange = { pages = it },
                label = { Text(stringResource(R.string.pages_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            val authorText = if (author.isBlank()) stringResource(R.string.unknown) else author
            val countryText = if (country.isBlank()) stringResource(R.string.unknown) else country
            val yearText = if (year.isBlank()) stringResource(R.string.unknown) else year
            val languageText = if (language.isBlank()) stringResource(R.string.unknown) else language
            val pagesText = if (pages.isBlank()) stringResource(R.string.unknown) else pages

            Button(
                onClick = {
                    if (book != null) {
                        val updatedBook = book.copy(
                            title = title,
                            author = authorText,
                            country = countryText,
                            year = yearText,
                            language = languageText,
                            pages = pagesText
                        )
                        viewModel.updateBook(updatedBook)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isUpdateEnabled
            ) {
                Text(stringResource(R.string.update))
            }
        }
    }
}
