package com.example.athkar.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.athkar.data.local.entities.SurahEntity

@Composable
fun SurahItem(
    surah: SurahEntity,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = surah.nameAr,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    surah.nameEn?.let { name ->
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Surah info
                    Column(horizontalAlignment = Alignment.End) {
                        surah.surahNumber?.let { num ->
                            Text(
                                text = "سورة $num",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        surah.ayatCount?.let { count ->
                            Text(
                                text = "$count آية",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    IconButton(onClick = onFavoriteToggle) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            
            // Revelation info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                surah.revelationType?.let { type ->
                    SuggestionChip(
                        onClick = { },
                        label = { Text(if (type == "Makkah") "مكية" else "مدنية") }
                    )
                }
                surah.juzNumber?.let { juz ->
                    Spacer(modifier = Modifier.width(8.dp))
                    SuggestionChip(
                        onClick = { },
                        label = { Text("الجزء $juz") }
                    )
                }
            }
            
            // Expand/collapse button
            TextButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (expanded) "إخفاء النص" else "عرض النص")
            }
            
            // Full text (expandable)
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = surah.textAr,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                
                surah.virtues?.let { virtues ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = virtues,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
