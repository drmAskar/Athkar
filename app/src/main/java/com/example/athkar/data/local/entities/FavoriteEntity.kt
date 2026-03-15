package com.example.athkar.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemType: String, // "athkar" or "surah"
    val itemId: String,
    val createdAt: Long = System.currentTimeMillis()
)
