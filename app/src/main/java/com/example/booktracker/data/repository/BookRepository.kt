package com.example.booktracker.data.repository

import com.example.booktracker.data.local.BookDao
import com.example.booktracker.data.local.BookEntity
import kotlinx.coroutines.flow.Flow

class BookRepository(private val dao: BookDao) {
    val allBooks: Flow<List<BookEntity>> = dao.getAllBooks()
    suspend fun insertBook(book: BookEntity) = dao.insert(book)
    suspend fun deleteById(bookId: Long) = dao.deleteById(bookId)
    fun getBookById(bookId: Long): Flow<BookEntity?> = dao.getById(bookId)
    suspend fun updateBook(book: BookEntity) = dao.update(book)

}
