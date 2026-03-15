package com.example.athkar.data.seed

import android.content.Context
import com.example.athkar.data.local.database.AthkarDatabase
import com.example.athkar.data.local.entities.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class SeedDataLoader(private val context: Context, private val database: AthkarDatabase) {
    
    private val gson = Gson()
    
    suspend fun seedAllIfNeeded() = withContext(Dispatchers.IO) {
        val existingCategories = database.categoryDao().getCategoryById("morning")
        if (existingCategories == null) {
            seedCategories()
            seedAthkar()
            seedSurahs()
            seedReminders()
        }
    }
    
    private suspend fun seedCategories() {
        val json = loadJson("categories.json")
        val listType = object : TypeToken<List<CategoryEntityData>>() {}.type
        val categoriesData: List<CategoryEntityData> = gson.fromJson(json, listType)
        
        val categories = categoriesData.map {
            CategoryEntity(
                id = it.id,
                nameAr = it.nameAr,
                nameEn = it.nameEn,
                icon = it.icon,
                sortOrder = it.sortOrder
            )
        }
        database.categoryDao().insertAll(categories)
    }
    
    private suspend fun seedAthkar() {
        val categoryFiles = listOf(
            "athkar_morning.json" to "morning",
            "athkar_evening.json" to "evening",
            "athkar_sleep.json" to "sleep",
            "athkar_post_prayer.json" to "post_prayer"
        )
        
        val allAthkar = mutableListOf<AthkarEntity>()
        
        for ((file, _) in categoryFiles) {
            val json = loadJson(file)
            val listType = object : TypeToken<List<AthkarEntityData>>() {}.type
            val athkarData: List<AthkarEntityData> = gson.fromJson(json, listType)
            
            athkarData.forEach {
                allAthkar.add(AthkarEntity(
                    id = it.id,
                    categoryId = it.categoryId,
                    titleAr = it.titleAr,
                    textAr = it.textAr,
                    count = it.count,
                    source = it.source,
                    sourceReference = it.sourceReference,
                    virtues = it.virtues,
                    sortOrder = it.sortOrder
                ))
            }
        }
        
        database.athkarDao().insertAll(allAthkar)
    }
    
    private suspend fun seedSurahs() {
        val json = loadJson("surahs.json")
        val listType = object : TypeToken<List<SurahEntityData>>() {}.type
        val surahsData: List<SurahEntityData> = gson.fromJson(json, listType)
        
        val surahs = surahsData.map {
            SurahEntity(
                id = it.id,
                nameAr = it.nameAr,
                nameEn = it.nameEn,
                surahNumber = it.surahNumber,
                ayatCount = it.ayatCount,
                revelationType = it.revelationType,
                juzNumber = it.juzNumber,
                textAr = it.textAr,
                virtues = it.virtues,
                sourceReference = it.sourceReference
            )
        }
        database.surahDao().insertAll(surahs)
    }
    
    private suspend fun seedReminders() {
        val defaultReminders = listOf(
            ReminderEntity("morning", "morning", "05:00", true, null),
            ReminderEntity("evening", "evening", "17:00", true, null),
            ReminderEntity("sleep", "sleep", "22:00", true, null)
        )
        defaultReminders.forEach { database.reminderDao().upsert(it) }
    }
    
    private fun loadJson(fileName: String): String {
        val inputStream = context.assets.open("data/$fileName")
        return InputStreamReader(inputStream).readText()
    }
    
    // Data classes for JSON parsing
    private data class CategoryEntityData(
        val id: String,
        val nameAr: String,
        val nameEn: String?,
        val icon: String?,
        val sortOrder: Int
    )
    
    private data class AthkarEntityData(
        val id: String,
        val categoryId: String,
        val titleAr: String,
        val textAr: String,
        val count: Int,
        val source: String?,
        val sourceReference: String?,
        val virtues: String?,
        val sortOrder: Int
    )
    
    private data class SurahEntityData(
        val id: String,
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
}
