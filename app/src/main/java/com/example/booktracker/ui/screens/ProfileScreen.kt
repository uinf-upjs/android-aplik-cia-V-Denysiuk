package com.example.booktracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.booktracker.R
import com.example.booktracker.ui.theme.*
import com.example.booktracker.viewmodel.book.BookViewModel
import com.example.booktracker.viewmodel.review.ReviewViewModel

@Composable
fun ProfileScreen(
    bookViewModel: BookViewModel,
    reviewViewModel: ReviewViewModel
) {
    val books by bookViewModel.books.collectAsState()
    val reviews by reviewViewModel.allReviews.collectAsState(initial = emptyList())

    val readBooks = books.filter { book ->
        val review = reviews.find { it.bookId == book.id }
        review?.isRead == true
    }

    val totalPagesRead = readBooks.sumOf { book ->
        reviews.find { it.bookId == book.id }?.pagesRead ?: 0
    }

    val recentlyReadBooks = readBooks.takeLast(5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppPadding.medium)
    ) {
        Text(
            text = stringResource(R.string.profile_title),
            style = MaterialTheme.typography.titleLarge,
            fontSize = AppTextSize.title
        )

        Spacer(modifier = Modifier.height(AppPadding.medium))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(AppSize.cardElevation)
        ) {
            Column(modifier = Modifier.padding(AppPadding.medium)) {
                StatisticItem(
                    label = stringResource(R.string.stat_read_books),
                    value = readBooks.size.toFloat(),
                    maxValue = books.size.toFloat(),
                    color = MaterialTheme.colorScheme.primary
                )
                StatisticItem(
                    label = stringResource(R.string.stat_pages_read),
                    value = totalPagesRead.toFloat(),
                    maxValue = books.sumOf { it.pages.toIntOrNull() ?: 0 }.toFloat(),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        Spacer(modifier = Modifier.height(AppPadding.medium))

        Text(
            text = stringResource(R.string.recently_read_title),
            style = MaterialTheme.typography.titleMedium,
            fontSize = AppTextSize.large
        )
        Spacer(modifier = Modifier.height(AppPadding.small))

        if (recentlyReadBooks.isEmpty()) {
            Text(
                text = stringResource(R.string.no_recent_books),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = AppTextSize.medium
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(AppPadding.small),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(recentlyReadBooks) { book ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(AppSize.cardElevation)
                    ) {
                        Box(modifier = Modifier.padding(AppPadding.medium)) {
                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = AppTextSize.medium
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun StatisticItem(label: String, value: Float, maxValue: Float, color: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppPadding.small)
    ) {
        Text(
            text = "$label: ${value.toInt()}",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = AppTextSize.medium
        )
        LinearProgressIndicator(
            progress = if (maxValue > 0) (value / maxValue) else 0f,
            color = color,
            trackColor = color.copy(alpha = 0.3f),
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .padding(top = AppPadding.small)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}