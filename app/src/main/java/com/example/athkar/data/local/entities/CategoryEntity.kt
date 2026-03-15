package com.example.athkar.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val nameAr: String,
    val nameEn: String?,
    val icon: String?,
    val sortOrder: Int = 0
)
