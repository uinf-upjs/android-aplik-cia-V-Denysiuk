package com.example.booktracker.viewmodel.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.local.review.ReviewEntity
import com.example.booktracker.data.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReviewViewModel(private val repo: ReviewRepository) : ViewModel() {

    fun getReviewForBook(bookId: Long): Flow<ReviewEntity?> {
        return repo.getReviewForBook(bookId)
    }

    fun saveReview(review: ReviewEntity) {
        viewModelScope.launch {
            if (review.id == 0L) repo.insertReview(review)
            else repo.updateReview(review)
        }
    }

    fun markAsRead(review: ReviewEntity, isRead: Boolean) {
        viewModelScope.launch {
            repo.updateReview(review.copy(isRead = isRead))
        }
    }

    val allReviews: Flow<List<ReviewEntity>> = repo.allReviews


}
