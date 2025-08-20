package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.booktracker.Book
import com.example.booktracker.Screen
import com.example.booktracker.ui.theme.AppPadding
import com.example.booktracker.ui.theme.AppSize

@Composable
fun BookListScreen(navController: NavController) {
    val books = listOf(
        Book("The Divine Comedy", "Dante Alighieri", "Italy", 1315),
        Book("Pride and Prejudice", "Jane Austen", "United Kingdom", 1813),
        Book("Don Quijote De La Mancha", "Miguel de Cervantes", "Spain", 1610),
        Book("Iliad", "Homer", "Greece", -735),
        Book("Hamlet", "William Shakespeare", "England", 1603)
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddBook.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Book")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(AppPadding.medium),
            verticalArrangement = Arrangement.spacedBy(AppPadding.small)
        ) {
            items(books) { book ->
                BookCard(book)
            }
        }
    }
}

@Composable
fun BookCard(book: Book) {
    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(AppSize.cardElevation)
    ) {
        Column(
            modifier = Modifier.padding(AppPadding.medium)
        ) {
            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Country: ${book.country}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Year: ${book.year}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
