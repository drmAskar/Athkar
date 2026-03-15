package com.example.athkar.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.athkar.data.local.entities.AthkarEntity

@Composable
fun AthkarItem(
    athkar: AthkarEntity,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    onCountComplete: () -> Unit,
    currentCount: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
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
            // Title row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = athkar.titleAr,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Row {
                    // Source badge
                    athkar.source?.let { source ->
                        SuggestionChip(
                            onClick = { },
                            label = { Text(source, style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    // Favorite toggle
                    IconButton(onClick = onFavoriteToggle) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Arabic text
            Text(
                text = athkar.textAr,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // Counter section
            if (athkar.count > 1) {
                Spacer(modifier = Modifier.height(12.dp))
                CounterSection(
                    targetCount = athkar.count,
                    currentCount = currentCount,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    onReset = onReset,
                    onComplete = onCountComplete
                )
            }
            
            // Virtues (expandable)
            athkar.virtues?.let { virtues ->
                Spacer(modifier = Modifier.height(8.dp))
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

@Composable
fun CounterSection(
    targetCount: Int,
    currentCount: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    onComplete: () -> Unit
) {
    val progress = (currentCount.toFloat() / targetCount).coerceIn(0f, 1f)
    val isComplete = currentCount >= targetCount
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Counter buttons
        Row(verticalAlignment = Alignment.CenterVertically) {
            FilledTonalButton(
                onClick = onDecrement,
                enabled = currentCount > 0,
                modifier = Modifier.size(40.dp)
            ) {
                Text("-")
            }
            
            Text(
                text = "$currentCount / $targetCount",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            FilledTonalButton(
                onClick = {
                    onIncrement()
                    if (currentCount + 1 >= targetCount) {
                        onComplete()
                    }
                },
                modifier = Modifier.size(40.dp)
            ) {
                Text("+")
            }
        }
        
        // Progress indicator
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .padding(start = 16.dp),
        )
        
        if (isComplete) {
            Text(
                text = "✓",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
