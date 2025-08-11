package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.booktracker.ui.theme.AppPadding

@Composable
fun AddBookScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Add Book Screen",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(AppPadding.medium)
        )
    }
}
