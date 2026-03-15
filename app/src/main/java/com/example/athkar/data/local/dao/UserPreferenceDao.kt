package com.example.athkar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athkar.data.local.entities.UserPreferenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferenceDao {
    @Query("SELECT value FROM user_preferences WHERE key = :key")
    suspend fun getValue(key: String): String?

    @Query("SELECT value FROM user_preferences WHERE key = :key")
    fun getValueFlow(key: String): Flow<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setValue(preference: UserPreferenceEntity)

    @Query("DELETE FROM user_preferences WHERE key = :key")
    suspend fun delete(key: String)
}
