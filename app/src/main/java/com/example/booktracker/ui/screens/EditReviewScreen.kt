package com.example.booktracker.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.booktracker.R
import com.example.booktracker.data.local.review.ReviewEntity
import com.example.booktracker.ui.theme.AppPadding
import com.example.booktracker.viewmodel.book.BookViewModel
import com.example.booktracker.viewmodel.review.ReviewViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReviewScreen(
    navController: NavController,
    bookId: Long,
    bookViewModel: BookViewModel,
    reviewViewModel: ReviewViewModel
) {
    val book = bookViewModel.getBookById(bookId).collectAsState(initial = null).value
    val review = reviewViewModel.getReviewForBook(bookId).collectAsState(initial = null).value

    var rating by remember { mutableStateOf(0) }
    var reviewText by remember { mutableStateOf("") }
    var pagesRead by remember { mutableStateOf(0) }

    LaunchedEffect(book, review) {
        review?.let {
            rating = it.rating / 2
            reviewText = it.reviewText
            pagesRead = it.pagesRead
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_review)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        book?.let { safeBook ->
            val totalPages = safeBook.pages.toIntOrNull() ?: 0
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(AppPadding.medium),
                verticalArrangement = Arrangement.spacedBy(AppPadding.medium)
            ) {
                Text(stringResource(R.string.editing_review_for, safeBook.title))

                Text(stringResource(R.string.rating_label))
                AnimatedStarRatingEditable(rating = rating, onRatingChanged = { rating = it })

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text(stringResource(R.string.review_label)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(stringResource(R.string.pages_progress, pagesRead, totalPages))
                Slider(
                    value = pagesRead.toFloat(),
                    onValueChange = { pagesRead = it.toInt() },
                    valueRange = 0f..totalPages.toFloat()
                )

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val updatedReview = review?.copy(
                                rating = rating * 2,
                                reviewText = reviewText,
                                pagesRead = pagesRead
                            ) ?: ReviewEntity(
                                bookId = safeBook.id,
                                rating = rating * 2,
                                reviewText = reviewText,
                                pagesRead = pagesRead
                            )
                            reviewViewModel.saveReview(updatedReview)
                            navController.popBackStack()
                            snackbarHostState.showSnackbar("Review saved")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun AnimatedStarRatingEditable(rating: Int, onRatingChanged: (Int) -> Unit) {
    Row {
        repeat(5) { index ->
            val targetScale = if (index < rating) 1f else 0.8f
            val scale by animateFloatAsState(
                targetValue = targetScale,
                animationSpec = tween(durationMillis = 300)
            )
            IconButton(onClick = { onRatingChanged(index + 1) }) {
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
}

