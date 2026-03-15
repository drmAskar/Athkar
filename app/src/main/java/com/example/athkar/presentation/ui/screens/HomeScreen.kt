package com.example.athkar.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.athkar.data.local.entities.CategoryEntity
import com.example.athkar.presentation.ui.components.CategoryCard

@Composable
fun HomeScreen(
    categories: List<CategoryEntity>,
    lastReadCategory: String?,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Welcome section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "أذكار المسلم",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "حصّن نفسك بأذكار الصباح والمساء",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
        
        // Continue reading card
        if (lastReadCategory != null) {
            item {
                val lastCategory = categories.find { it.id == lastReadCategory }
                lastCategory?.let {
                    Card(
                        onClick = { onCategoryClick(it.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "متابعة القراءة",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = it.nameAr,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }
        }
        
        // Categories section
        item {
            Text(
                text = "الأقسام",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        
        items(categories, key = { it.id }) { category ->
            CategoryCard(
                category = category,
                itemCount = 0, // Would need to fetch from ViewModel
                onClick = { onCategoryClick(category.id) }
            )
        }
    }
}
