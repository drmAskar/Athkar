package com.example.athkar.data.local.dao

import androidx.room.*
import com.example.athkar.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY sortOrder ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: String): CategoryEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)
}

@Dao
interface AthkarDao {
    @Query("SELECT * FROM athkar WHERE categoryId = :categoryId ORDER BY sortOrder ASC")
    fun getAthkarByCategory(categoryId: String): Flow<List<AthkarEntity>>
    
    @Query("SELECT * FROM athkar WHERE id = :id")
    suspend fun getAthkarById(id: String): AthkarEntity?
    
    @Query("SELECT * FROM athkar")
    fun getAllAthkar(): Flow<List<AthkarEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(athkar: List<AthkarEntity>)
}

@Dao
interface SurahDao {
    @Query("SELECT * FROM surahs ORDER BY surahNumber ASC")
    fun getAllSurahs(): Flow<List<SurahEntity>>
    
    @Query("SELECT * FROM surahs WHERE id = :id")
    suspend fun getSurahById(id: String): SurahEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(surahs: List<SurahEntity>)
}

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY createdAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    
    @Query("SELECT * FROM favorites WHERE itemType = :itemType")
    fun getFavoritesByType(itemType: String): Flow<List<FavoriteEntity>>
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE itemType = :itemType AND itemId = :itemId)")
    fun isFavorite(itemType: String, itemId: String): Flow<Boolean>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)
    
    @Query("DELETE FROM favorites WHERE itemType = :itemType AND itemId = :itemId")
    suspend fun delete(itemType: String, itemId: String)
}

@Dao
interface DailyProgressDao {
    @Query("SELECT * FROM daily_progress WHERE date = :date")
    fun getProgressByDate(date: String): Flow<List<DailyProgressEntity>>
    
    @Query("SELECT * FROM daily_progress WHERE date = :date AND categoryId = :categoryId")
    suspend fun getProgressByDateAndCategory(date: String, categoryId: String): DailyProgressEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: DailyProgressEntity)
    
    @Query("UPDATE daily_progress SET completedCount = completedCount + 1 WHERE date = :date AND categoryId = :categoryId")
    suspend fun incrementProgress(date: String, categoryId: String)
}

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders")
    fun getAllReminders(): Flow<List<ReminderEntity>>
    
    @Query("SELECT * FROM reminders WHERE enabled = 1")
    fun getEnabledReminders(): Flow<List<ReminderEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reminders: List<ReminderEntity>)
    
    @Update
    suspend fun update(reminder: ReminderEntity)
    
    @Query("DELETE FROM reminders WHERE id = :id")
    suspend fun delete(id: String)
}

@Dao
interface LastReadDao {
    @Query("SELECT * FROM last_read WHERE id = 0")
    fun getLastRead(): Flow<LastReadEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lastRead: LastReadEntity)
}

@Dao
interface UserPreferenceDao {
    @Query("SELECT * FROM user_preferences WHERE `key` = :key")
    suspend fun get(key: String): UserPreferenceEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(preference: UserPreferenceEntity)
    
    @Query("DELETE FROM user_preferences WHERE `key` = :key")
    suspend fun delete(key: String)
}
