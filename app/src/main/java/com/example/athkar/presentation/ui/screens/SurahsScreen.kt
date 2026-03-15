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

@Composable
fun SurahsScreen(
    surahs: List<SurahEntity>,
    isFavorite: (id: String) -> Flow<Boolean>,
    onFavoriteToggle: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (surahs.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(surahs, key = { it.id }) { surah ->
                val isFav by isFavorite("surah_${surah.id}").collectAsState(initial = false)
                
                SurahItem(
                    surah = surah,
                    isFavorite = isFav,
                    onFavoriteToggle = { onFavoriteToggle("surah_${surah.id}") }
                )
            }
        }
    }
}
