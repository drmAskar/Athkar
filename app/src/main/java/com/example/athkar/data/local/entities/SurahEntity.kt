package com.example.athkar.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surahs")
data class SurahEntity(
    @PrimaryKey
    val id: String,
    val nameAr: String,
    val nameEn: String?,
    val surahNumber: Int?,
    val ayatCount: Int?,
    val revelationType: String?, // "Makkah" or "Madinah"
    val juzNumber: Int?,
    val textAr: String,
    val virtues: String?,
    val sourceReference: String?
)
