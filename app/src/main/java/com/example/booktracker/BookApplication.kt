package com.example.booktracker

import android.app.Application
import com.example.booktracker.data.local.BookDatabase
import com.example.booktracker.data.repository.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BookApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { BookDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { BookRepository(database.bookDao()) }
}
