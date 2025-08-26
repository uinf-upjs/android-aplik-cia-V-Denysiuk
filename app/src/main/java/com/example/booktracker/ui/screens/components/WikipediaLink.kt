package com.example.booktracker.ui.screens.components

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.booktracker.R
import com.example.booktracker.ui.theme.AppPadding

@Composable
fun WikipediaLink(url: String) {
    val context = LocalContext.current
    Text(
        text = stringResource(R.string.wikipedia_link),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
            .padding(vertical = AppPadding.small)
    )
}
