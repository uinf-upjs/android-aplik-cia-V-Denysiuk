package com.example.booktracker.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.booktracker.Screen
import com.example.booktracker.ui.theme.*
import com.example.booktracker.viewmodel.book.BookViewModel
import com.example.booktracker.viewmodel.review.ReviewViewModel
import com.example.booktracker.R
import com.example.booktracker.data.local.review.ReviewEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    navController: NavController,
    bookId: Long,
    bookViewModel: BookViewModel,
    reviewViewModel: ReviewViewModel
) {
    val book by bookViewModel.getBookById(bookId).collectAsState(initial = null)
    val review by reviewViewModel.getReviewForBook(bookId).collectAsState(initial = null)
    val scrollState = rememberScrollState()

    var isRead by remember { mutableStateOf(review?.isRead ?: false) }


    LaunchedEffect(review) {
        review?.let { isRead = it.isRead }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book?.title ?: stringResource(R.string.book_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (book != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(AppPadding.medium)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(AppPadding.medium)
            ) {
                Text("${stringResource(R.string.author_label)}: ${book!!.author}")
                Text("${stringResource(R.string.country_label)}: ${book!!.country}")
                Text("${stringResource(R.string.year_label)}: ${book!!.year}")
                Text("${stringResource(R.string.language_label)}: ${book!!.language}")
                Text("${stringResource(R.string.pages_label)}: ${book!!.pages}")

                Divider()

                review?.let { safeReview ->
                    Text(stringResource(R.string.rating_label))
                    AnimatedStarRatingDisplay(rating = safeReview.rating / 2)

                    Text(stringResource(R.string.review_label))
                    Text(safeReview.reviewText)

                    val totalPages = book!!.pages.toIntOrNull() ?: 0
                    val progressPercent =
                        if (totalPages > 0) (safeReview.pagesRead * 100 / totalPages) else 0
                    Text(stringResource(R.string.progress_label, progressPercent))
                }

                Spacer(modifier = Modifier.height(AppPadding.medium))

                Button(
                    onClick = { navController.navigate(Screen.EditReview.createRoute(book!!.id)) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.edit_review))
                }
                Button(
                    onClick = {
                        if (review != null) {
                            reviewViewModel.markAsRead(review!!, !isRead)
                            isRead = !isRead
                        } else {
                            val newReview = ReviewEntity(
                                bookId = book!!.id,
                                rating = 0,
                                reviewText = "",
                                pagesRead = 0,
                                isRead = true
                            )
                            reviewViewModel.saveReview(newReview)
                            isRead = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRead) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(if (isRead) stringResource(R.string.mark_as_unread) else stringResource(R.string.mark_as_read))
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}


@Composable
fun AnimatedStarRatingDisplay(rating: Int) {
    Row {
        repeat(5) { index ->
            val targetScale = if (index < rating) 1f else 0.8f
            val scale by animateFloatAsState(
                targetValue = targetScale,
                animationSpec = tween(durationMillis = 500)
            )
            Icon(
                imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (index < rating) Color(0xFFFFD700)
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.scale(scale)
            )
        }
    }
}
