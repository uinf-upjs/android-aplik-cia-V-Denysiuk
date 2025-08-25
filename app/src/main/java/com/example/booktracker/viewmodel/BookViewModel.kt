package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.local.BookEntity
import com.example.booktracker.data.repository.BookRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookViewModel(private val repo: BookRepository) : ViewModel() {
    val books = repo.allBooks.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun insertBook(book: BookEntity) = viewModelScope.launch {
        repo.insertBook(book)
    }

    fun deleteBookById(bookId: Long) = viewModelScope.launch {
        repo.deleteById(bookId)
    }

    fun getBookById(bookId: Long): Flow<BookEntity?> {
        return repo.getBookById(bookId)
    }

    fun updateBook(book: BookEntity) = viewModelScope.launch {
        repo.updateBook(book)
    }
}
