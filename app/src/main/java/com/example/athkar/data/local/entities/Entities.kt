package com.example.athkar.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String?,
    val icon: String?,
    val sortOrder: Int = 0
)

@Entity(tableName = "athkar")
data class AthkarEntity(
    @PrimaryKey val id: String,
    val categoryId: String,
    val titleAr: String,
    val textAr: String,
    val count: Int = 1,
    val source: String?,
    val sourceReference: String?,
    val virtues: String?,
    val sortOrder: Int = 0
)

@Entity(tableName = "surahs")
data class SurahEntity(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String?,
    val surahNumber: Int?,
    val ayatCount: Int?,
    val revelationType: String?,
    val juzNumber: Int?,
    val textAr: String,
    val virtues: String?,
    val sourceReference: String?
)

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val itemType: String, // 'athkar' or 'surah'
    val itemId: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "daily_progress")
data class DailyProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String, // Format: yyyy-MM-dd
    val categoryId: String,
    val completedCount: Int = 0,
    val totalCount: Int = 0
)

@Entity(tableName = "user_preferences")
data class UserPreferenceEntity(
    @PrimaryKey val key: String,
    val value: String
)

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: String,
    val type: String, // 'morning', 'evening', 'sleep'
    val time: String, // Format: HH:mm
    val enabled: Boolean = true,
    val days: String = "" // JSON array of days (empty = daily)
)

@Entity(tableName = "last_read")
data class LastReadEntity(
    @PrimaryKey val id: Int = 0,
    val categoryId: String?,
    val itemId: String?,
    val itemType: String?, // 'athkar' or 'surah'
    val scrollPosition: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
