package com.example.booktracker.data.repository

import com.example.booktracker.data.local.review.ReviewDao
import com.example.booktracker.data.local.review.ReviewEntity
import kotlinx.coroutines.flow.Flow

class ReviewRepository(private val dao: ReviewDao) {
    fun getReviewForBook(bookId: Long): Flow<ReviewEntity?> = dao.getReviewForBook(bookId)
    suspend fun insertReview(review: ReviewEntity) = dao.insert(review)
    suspend fun updateReview(review: ReviewEntity) = dao.update(review)
}
