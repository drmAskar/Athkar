package com.example.athkar.data.local.database

import android.content.Context
import androidx.room.*
import com.example.athkar.data.local.dao.*
import com.example.athkar.data.local.entities.*

@Database(
    entities = [
        CategoryEntity::class,
        AthkarEntity::class,
        SurahEntity::class,
        FavoriteEntity::class,
        DailyProgressEntity::class,
        UserPreferenceEntity::class,
        ReminderEntity::class,
        LastReadEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun athkarDao(): AthkarDao
    abstract fun surahDao(): SurahDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun dailyProgressDao(): DailyProgressDao
    abstract fun reminderDao(): ReminderDao
    abstract fun lastReadDao(): LastReadDao
    abstract fun userPreferenceDao(): UserPreferenceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "athkar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
