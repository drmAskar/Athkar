package com.example.athkar.data.repository

import android.content.Context
import com.example.athkar.data.local.database.AppDatabase
import com.example.athkar.data.local.entities.*
import com.example.athkar.data.local.dao.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AthkarRepository(private val context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val gson = Gson()
    
    // Categories
    fun getCategories(): Flow<List<CategoryEntity>> = database.categoryDao().getAllCategories()
    
    // Athkar
    fun getAthkarByCategory(categoryId: String): Flow<List<AthkarEntity>> = 
        database.athkarDao().getAthkarByCategory(categoryId)
    
    fun getAllAthkar(): Flow<List<AthkarEntity>> = database.athkarDao().getAllAthkar()
    
    suspend fun getAthkarById(id: String): AthkarEntity? = database.athkarDao().getAthkarById(id)
    
    // Surahs
    fun getAllSurahs(): Flow<List<SurahEntity>> = database.surahDao().getAllSurahs()
    
    suspend fun getSurahById(id: String): SurahEntity? = database.surahDao().getSurahById(id)
    
    // Favorites
    fun getAllFavorites(): Flow<List<FavoriteEntity>> = database.favoriteDao().getAllFavorites()
    
    fun isFavorite(itemType: String, itemId: String): Flow<Boolean> = 
        database.favoriteDao().isFavorite(itemType, itemId)
    
    suspend fun addToFavorites(itemType: String, itemId: String) {
        database.favoriteDao().insert(FavoriteEntity(itemType = itemType, itemId = itemId))
    }
    
    suspend fun removeFromFavorites(itemType: String, itemId: String) {
        database.favoriteDao().delete(itemType, itemId)
    }
    
    suspend fun toggleFavorite(itemType: String, itemId: String): Boolean {
        val isFav = database.favoriteDao().isFavorite(itemType, itemId)
        // Since we can't easily check flow synchronously, we'll handle this in ViewModel
        database.favoriteDao().insert(FavoriteEntity(itemType = itemType, itemId = itemId))
        return true
    }
    
    // Progress
    fun getTodayProgress(): Flow<List<DailyProgressEntity>> {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        return database.dailyProgressDao().getProgressByDate(today)
    }
    
    suspend fun incrementProgress(categoryId: String) {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        val existing = database.dailyProgressDao().getProgressByDateAndCategory(today, categoryId)
        if (existing == null) {
            database.dailyProgressDao().insert(DailyProgressEntity(
                date = today,
                categoryId = categoryId,
                completedCount = 1
            ))
        } else {
            database.dailyProgressDao().incrementProgress(today, categoryId)
        }
    }
    
    // Reminders
    fun getAllReminders(): Flow<List<ReminderEntity>> = database.reminderDao().getAllReminders()
    
    fun getEnabledReminders(): Flow<List<ReminderEntity>> = database.reminderDao().getEnabledReminders()
    
    suspend fun updateReminder(reminder: ReminderEntity) {
        database.reminderDao().update(reminder)
    }
    
    // Last Read
    fun getLastRead(): Flow<LastReadEntity?> = database.lastReadDao().getLastRead()
    
    suspend fun updateLastRead(categoryId: String?, itemId: String?, itemType: String?, scrollPosition: Int = 0) {
        database.lastReadDao().insert(LastReadEntity(
            id = 0,
            categoryId = categoryId,
            itemId = itemId,
            itemType = itemType,
            scrollPosition = scrollPosition
        ))
    }
    
    // Seed data from assets
    suspend fun seedDataIfNeeded() {
        // Check if data already exists
        val categories = database.categoryDao().getCategoryById("morning")
        if (categories == null) {
            seedData()
        }
    }
    
    private suspend fun seedData() {
        // Seed categories
        val categoriesJson = loadJsonFromAsset("data/categories.json")
        val categories = gson.fromJson<List<CategoryEntity>>(categoriesJson, object : TypeToken<List<CategoryEntity>>(){}.type)
        database.categoryDao().insertAll(categories)
        
        // Seed athkar
        val morningAthkar = gson.fromJson<List<AthkarEntity>>(
            loadJsonFromAsset("data/athkar_morning.json"),
            object : TypeToken<List<AthkarEntity>>(){}.type
        )
        database.athkarDao().insertAll(morningAthkar)
        
        val eveningAthkar = gson.fromJson<List<AthkarEntity>>(
            loadJsonFromAsset("data/athkar_evening.json"),
            object : TypeToken<List<AthkarEntity>>(){}.type
        )
        database.athkarDao().insertAll(eveningAthkar)
        
        val sleepAthkar = gson.fromJson<List<AthkarEntity>>(
            loadJsonFromAsset("data/athkar_sleep.json"),
            object : TypeToken<List<AthkarEntity>>(){}.type
        )
        database.athkarDao().insertAll(sleepAthkar)
        
        val postPrayerAthkar = gson.fromJson<List<AthkarEntity>>(
            loadJsonFromAsset("data/athkar_post_prayer.json"),
            object : TypeToken<List<AthkarEntity>>(){}.type
        )
        database.athkarDao().insertAll(postPrayerAthkar)
        
        // Seed surahs
        val surahs = gson.fromJson<List<SurahEntity>>(
            loadJsonFromAsset("data/surahs.json"),
            object : TypeToken<List<SurahEntity>>(){}.type
        )
        database.surahDao().insertAll(surahs)
        
        // Seed default reminders
        val defaultReminders = listOf(
            ReminderEntity(id = "morning", type = "morning", time = "05:00", enabled = false),
            ReminderEntity(id = "evening", type = "evening", time = "17:00", enabled = false),
            ReminderEntity(id = "sleep", type = "sleep", time = "22:00", enabled = false)
        )
        database.reminderDao().insertAll(defaultReminders)
    }
    
    private fun loadJsonFromAsset(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
