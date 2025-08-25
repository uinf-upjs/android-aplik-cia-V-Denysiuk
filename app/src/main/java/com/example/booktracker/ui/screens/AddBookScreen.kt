package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import com.example.booktracker.ui.theme.AppPadding
import androidx.compose.ui.text.input.*
import com.example.booktracker.ui.theme.AppTextSize

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
                title = { Text("ADD BOOK",
                    style = TextStyle(fontSize = AppTextSize.title)
                ) },
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
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = AppTextSize.medium)
            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = AppTextSize.medium)
            )
            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("Country") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = AppTextSize.medium)
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = AppTextSize.medium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(AppPadding.large))

            Button(
                onClick = {
                    // тут потім зробити room, зараз лише повернення на головний екран
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save",
                    style = TextStyle(fontSize = AppTextSize.large))
            }
        }
    }
}

