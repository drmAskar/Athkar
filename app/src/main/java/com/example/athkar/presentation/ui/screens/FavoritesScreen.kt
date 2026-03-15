package com.example.athkar.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athkar.data.local.entities.AthkarEntity
import com.example.athkar.data.local.entities.FavoriteEntity
import com.example.athkar.data.local.entities.SurahEntity
import com.example.athkar.presentation.ui.components.AthkarItem
import com.example.athkar.presentation.ui.components.SurahItem
import kotlinx.coroutines.flow.Flow

@Composable
fun FavoritesScreen(
    favorites: List<FavoriteEntity>,
    favoriteAthkar: List<AthkarEntity>,
    favoriteSurahs: List<SurahEntity>,
    isFavorite: (itemType: String, id: String) -> Flow<Boolean>,
    onFavoriteToggle: (itemType: String, id: String) -> Unit,
    currentCounter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "لا توجد مفضلات",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "أضف الأذكار والسور للمفضلة للوصول السريع",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            // Favorite Athkar
            items(favoriteAthkar, key = { "athkar_${it.id}" }) { athkar ->
                val isFav by isFavorite("athkar", athkar.id).collectAsState(initial = false)
                AthkarItem(
                    athkar = athkar,
                    isFavorite = isFav,
                    onFavoriteToggle = { onFavoriteToggle("athkar", athkar.id) },
                    onCountComplete = { },
                    currentCount = currentCounter,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    onReset = onReset
                )
            }
            
            // Favorite Surahs
            items(favoriteSurahs, key = { "surah_${it.id}" }) { surah ->
                val isFav by isFavorite("surah", surah.id).collectAsState(initial = false)
                SurahItem(
                    surah = surah,
                    isFavorite = isFav,
                    onFavoriteToggle = { onFavoriteToggle("surah", surah.id) }
                )
            }
        }
    }
}
