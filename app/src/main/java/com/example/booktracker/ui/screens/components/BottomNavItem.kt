package com.example.booktracker.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Books : BottomNavItem("books", Icons.Default.Home, "Books")
    object Read : BottomNavItem("read", Icons.Default.Check, "Read")
    object Add : BottomNavItem("add", Icons.Default.Add, "Add")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}