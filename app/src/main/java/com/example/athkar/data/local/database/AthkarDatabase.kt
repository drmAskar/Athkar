package com.example.athkar.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.athkar.data.local.dao.*
import com.example.athkar.data.local.entities.*

@Database(
    entities = [
        CategoryEntity::class,
        AthkarEntity::class,
        SurahEntity::class,
        FavoriteEntity::class,
        UserPreferenceEntity::class,
        ReminderEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AthkarDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun athkarDao(): AthkarDao
    abstract fun surahDao(): SurahDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userPreferenceDao(): UserPreferenceDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var INSTANCE: AthkarDatabase? = null

        fun getDatabase(context: Context): AthkarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AthkarDatabase::class.java,
                    "athkar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
