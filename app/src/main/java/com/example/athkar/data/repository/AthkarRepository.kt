package com.example.athkar.data.repository

import android.content.Context
import com.example.athkar.data.local.database.AppDatabase
import com.example.athkar.data.local.entities.*
import com.example.athkar.data.local.dao.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
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
    fun getAthkarByCategory(categoryId: String): Flow<List<AthkarEntity>> = database.athkarDao().getAthkarByCategory(categoryId)
    fun getAllAthkar(): Flow<List<AthkarEntity>> = database.athkarDao().getAllAthkar()
    suspend fun getAthkarById(id: String): AthkarEntity? = database.athkarDao().getAthkarById(id)

    // Surahs
    fun getAllSurahs(): Flow<List<SurahEntity>> = database.surahDao().getAllSurahs()
    suspend fun getSurahById(id: String): SurahEntity? = database.surahDao().getSurahById(id)

    // Favorites
    fun getAllFavorites(): Flow<List<FavoriteEntity>> = database.favoriteDao().getAllFavorites()
    fun isFavorite(itemType: String, itemId: String): Flow<Boolean> = database.favoriteDao().isFavorite(itemType, itemId)
    suspend fun addToFavorites(itemType: String, itemId: String) {
        database.favoriteDao().insert(FavoriteEntity(itemType = itemType, itemId = itemId))
    }
    suspend fun removeFromFavorites(itemType: String, itemId: String) {
        database.favoriteDao().delete(itemType, itemId)
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
            database.dailyProgressDao().insert(
                DailyProgressEntity(
                    date = today,
                    categoryId = categoryId,
                    completedCount = 1
                )
            )
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
    suspend fun updateLastRead(
        categoryId: String?,
        itemId: String?,
        itemType: String?,
        scrollPosition: Int = 0
    ) {
        database.lastReadDao().insert(
            LastReadEntity(
                id = 0,
                categoryId = categoryId,
                itemId = itemId,
                itemType = itemType,
                scrollPosition = scrollPosition
            )
        )
    }

    // Seed data from assets
    suspend fun seedDataIfNeeded() {
        val categories = database.categoryDao().getCategoryById("morning")
        if (categories == null) {
            seedData()
        }
    }

    private suspend fun seedData() {
        // Seed categories with @SerializedName mapping
        val categoriesJson = loadJsonFromAsset("data/categories.json")
        val categoriesDataType = object : TypeToken<List<CategoryJsonData>>() {}.type
        val categoriesData: List<CategoryJsonData> = gson.fromJson(categoriesJson, categoriesDataType)
        val categories = categoriesData.mapNotNull {
            // Defensive: skip entries with missing required id
            if (it.id.isBlank()) return@mapNotNull null
            CategoryEntity(
                id = it.id,
                nameAr = it.nameAr?.ifEmpty { it.id } ?: it.id,
                nameEn = it.nameEn,
                icon = it.icon,
                sortOrder = it.sortOrder ?: 0
            )
        }
        database.categoryDao().insertAll(categories)

        // Seed athkar with @SerializedName mapping
        val categoryFiles = listOf(
            "data/athkar_morning.json",
            "data/athkar_evening.json",
            "data/athkar_sleep.json",
            "data/athkar_post_prayer.json"
        )
        for (file in categoryFiles) {
            val athkarJson = loadJsonFromAsset(file)
            val athkarDataType = object : TypeToken<List<AthkarJsonData>>() {}.type
            val athkarData: List<AthkarJsonData> = gson.fromJson(athkarJson, athkarDataType)
            val athkarList = athkarData.mapNotNull {
                // Defensive: skip entries with missing required fields (id, categoryId, titleAr, textAr)
                if (it.id.isBlank() || it.categoryId.isNullOrBlank() || it.titleAr.isNullOrBlank() || it.textAr.isNullOrBlank()) {
                    return@mapNotNull null
                }
                AthkarEntity(
                    id = it.id,
                    categoryId = it.categoryId,
                    titleAr = it.titleAr,
                    textAr = it.textAr,
                    count = it.count ?: 1,
                    source = it.source,
                    sourceReference = it.sourceReference,
                    virtues = it.virtues,
                    sortOrder = it.sortOrder ?: 0
                )
            }
            database.athkarDao().insertAll(athkarList)
        }

        // Seed surahs with @SerializedName mapping
        val surahsJson = loadJsonFromAsset("data/surahs.json")
        val surahsDataType = object : TypeToken<List<SurahJsonData>>() {}.type
        val surahsData: List<SurahJsonData> = gson.fromJson(surahsJson, surahsDataType)
        val surahs = surahsData.mapNotNull {
            // Defensive: skip entries with missing required fields (id, nameAr, textAr)
            if (it.id.isBlank() || it.nameAr.isNullOrBlank() || it.textAr.isNullOrBlank()) {
                return@mapNotNull null
            }
            SurahEntity(
                id = it.id,
                nameAr = it.nameAr.ifEmpty { it.id },
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

    // JSON data classes with @SerializedName annotations supporting BOTH snake_case and camelCase
    // Some seed files use snake_case (category_id), others use camelCase (categoryId)
    // @SerializedName alternate values ensure robust parsing from both formats

    private data class CategoryJsonData(
        val id: String,
        @SerializedName(value = "name_ar", alternate = ["nameAr"])
        val nameAr: String?,
        @SerializedName(value = "name_en", alternate = ["nameEn"])
        val nameEn: String?,
        val icon: String?,
        @SerializedName(value = "sort_order", alternate = ["sortOrder"])
        val sortOrder: Int?
    )

    private data class AthkarJsonData(
        val id: String,
        @SerializedName(value = "category_id", alternate = ["categoryId"])
        val categoryId: String?,
        @SerializedName(value = "title_ar", alternate = ["titleAr"])
        val titleAr: String?,
        @SerializedName(value = "text_ar", alternate = ["textAr"])
        val textAr: String?,
        val count: Int?,
        val source: String?,
        @SerializedName(value = "source_reference", alternate = ["sourceReference"])
        val sourceReference: String?,
        val virtues: String?,
        @SerializedName(value = "sort_order", alternate = ["sortOrder"])
        val sortOrder: Int?
    )

    private data class SurahJsonData(
        val id: String,
        @SerializedName(value = "name_ar", alternate = ["nameAr"])
        val nameAr: String?,
        @SerializedName(value = "name_en", alternate = ["nameEn"])
        val nameEn: String?,
        @SerializedName(value = "surah_number", alternate = ["surahNumber"])
        val surahNumber: Int?,
        @SerializedName(value = "ayat_count", alternate = ["ayatCount"])
        val ayatCount: Int?,
        @SerializedName(value = "revelation_type", alternate = ["revelationType"])
        val revelationType: String?,
        @SerializedName(value = "juz_number", alternate = ["juzNumber"])
        val juzNumber: Int?,
        @SerializedName(value = "text_ar", alternate = ["textAr"])
        val textAr: String?,
        val virtues: String?,
        @SerializedName(value = "source_reference", alternate = ["sourceReference"])
        val sourceReference: String?
    )
}
