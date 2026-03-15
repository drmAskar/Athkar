package com.example.athkar.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athkar.data.local.entities.AthkarEntity
import com.example.athkar.data.local.entities.CategoryEntity
import com.example.athkar.presentation.ui.components.*

@Composable
fun AthkarScreen(
    categories: List<CategoryEntity>,
    selectedCategory: CategoryEntity?,
    onCategorySelected: (CategoryEntity) -> Unit,
    athkarList: List<AthkarEntity>,
    currentAthkar: AthkarEntity?,
    currentCount: Int,
    onAthkarSelected: (AthkarEntity) -> Unit,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    isFavorite: (id: String) -> Boolean,
    onFavoriteToggle: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Category chips
        CategoryChipRow(
            categories = categories,
            selectedCategory = selectedCategory?.id,
            onCategorySelected = { categoryId ->
                categories.find { it.id == categoryId }?.let { onCategorySelected(it) }
            }
        )

        // Athkar list
        if (athkarList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(athkarList, key = { it.id }) { athkar ->
                    AthkarItem(
                        athkar = athkar,
                        isFavorite = isFavorite(athkar.id),
                        onFavoriteToggle = { onFavoriteToggle(athkar.id) },
                        onCountComplete = { onAthkarSelected(athkar) },
                        currentCount = if (currentAthkar?.id == athkar.id) currentCount else 0,
                        onIncrement = onIncrement,
                        onDecrement = onIncrement,
                        onReset = onReset
                    )
                }
            }
        }
    }
}
