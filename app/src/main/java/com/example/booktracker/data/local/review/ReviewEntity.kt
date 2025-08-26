package com.example.booktracker.data.local.review

import androidx.room.*
import com.example.booktracker.data.local.book.BookEntity

@Entity(
    tableName = "reviews",
    foreignKeys = [ForeignKey(
        entity = BookEntity::class,
        parentColumns = ["id"],
        childColumns = ["bookId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["bookId"])]
)
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: Long,
    val rating: Int = 0,
    val reviewText: String = "",
    val pagesRead: Int = 0
)
