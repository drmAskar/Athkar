package com.example.athkar.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "athkar",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId")]
)
data class AthkarEntity(
    @PrimaryKey
    val id: String,
    val categoryId: String,
    val titleAr: String,
    val textAr: String,
    val count: Int = 1,
    val source: String?,
    val sourceReference: String?,
    val virtues: String?,
    val sortOrder: Int = 0
)
