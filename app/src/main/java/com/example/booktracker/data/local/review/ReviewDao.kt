package com.example.booktracker.data.local.review

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews WHERE bookId = :bookId LIMIT 1")
    fun getReviewForBook(bookId: Long): Flow<ReviewEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(review: ReviewEntity): Long

    @Update
    suspend fun update(review: ReviewEntity)

    @Query("SELECT * FROM reviews")
    fun getAllReviews(): Flow<List<ReviewEntity>>


}
