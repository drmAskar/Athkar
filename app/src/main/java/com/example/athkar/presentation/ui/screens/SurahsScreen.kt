package com.example.athkar.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athkar.data.local.entities.SurahEntity
import com.example.athkar.presentation.ui.components.SurahItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.delay

@Composable
fun SurahsScreen(
    surahs: List<SurahEntity>,
    isFavorite: (id: String) -> Flow<Boolean>,
    onFavoriteToggle: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    LaunchedEffect(surahs) {
        if (surahs.isNotEmpty()) {
            isLoading = false
        } else {
            // Wait for data to load, timeout after 5 seconds
            delay(5000)
            if (surahs.isEmpty()) {
                isLoading = false
                hasError = true
            }
        }
    }

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        hasError -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "لم يتم تحميل السور",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "حاول إعادة فتح التطبيق",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(surahs, key = { it.id }) { surah ->
                    val isFav by isFavorite(surah.id).collectAsState(initial = false)
                    SurahItem(
                        surah = surah,
                        isFavorite = isFav,
                        onFavoriteToggle = { onFavoriteToggle(surah.id) }
                    )
                }
            }
        }
    }
}
