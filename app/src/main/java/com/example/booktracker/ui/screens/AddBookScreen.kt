package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.booktracker.ui.theme.AppPadding
import androidx.compose.ui.text.input.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(navController: NavController) {
    //локальні поля вводу
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add book") },
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
                label = { Text("Title") },
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
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    // тут потім зробити room, зараз лише повернення на головний екран
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

