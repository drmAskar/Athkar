package com.example.athkar.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey
    val id: String, // "morning", "evening", "sleep"
    val type: String,
    val time: String, // "HH:mm" format
    val enabled: Boolean = true,
    val days: String? = null // JSON array of days, null = daily
)
