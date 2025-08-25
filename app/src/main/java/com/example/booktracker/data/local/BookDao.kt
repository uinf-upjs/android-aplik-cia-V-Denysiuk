package com.example.booktracker.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(books: List<BookEntity>)

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteById(bookId: Long)

    @Query("SELECT * FROM books WHERE id = :bookId LIMIT 1")
    fun getById(bookId: Long): Flow<BookEntity?>

    @Update
    suspend fun update(book: BookEntity)
}
