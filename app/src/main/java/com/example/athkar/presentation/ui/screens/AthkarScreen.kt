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
import com.example.athkar.data.local.entities.CategoryEntity
import com.example.athkar.presentation.ui.components.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AthkarScreen(
    categories: List<CategoryEntity>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
    athkarByCategory: StateFlow<List<AthkarEntity>>,
    currentCounter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    isFavorite: (id: String) -> Flow<Boolean>,
    onFavoriteToggle: (id: String) -> Unit,
    onAthkarComplete: (AthkarEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Category chips
        CategoryChipRow(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected
        )
        
        // Athkar list
        val athkarList by athkarByCategory.collectAsState()
        
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
                    val isFav by isFavorite(athkar.id).collectAsState(initial = false)
                    
                    AthkarItem(
                        athkar = athkar,
                        isFavorite = isFav,
                        onFavoriteToggle = { onFavoriteToggle(athkar.id) },
                        onCountComplete = { onAthkarComplete(athkar) },
                        currentCount = currentCounter,
                        onIncrement = onIncrement,
                        onDecrement = onDecrement,
                        onReset = onReset
                    )
                }
            }
        }
    }
}
