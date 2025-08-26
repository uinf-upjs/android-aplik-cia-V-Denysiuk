package com.example.booktracker.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.booktracker.R
import com.example.booktracker.data.local.book.BookEntity
import com.example.booktracker.ui.theme.*

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
        Column(modifier = Modifier.padding(AppPadding.medium)) {
            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
            Text("${stringResource(R.string.author_label)}: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text("${stringResource(R.string.country_label)}: ${book.country}", style = MaterialTheme.typography.bodySmall)
            Text("${stringResource(R.string.year_label)}: ${book.year}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(AppPadding.small))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEditClick(book) }) {
                    Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.update), tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete), tint = MaterialTheme.colorScheme.error)
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(stringResource(R.string.delete_dialog_title)) },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeleteClick(book)
                            showDialog = false
                        }) { Text(stringResource(R.string.yes)) }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) { Text(stringResource(R.string.no)) }
                    }
                )
            }
        }
    }
}