package com.example.athkar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athkar.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY createdAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE itemType = :itemType AND itemId = :itemId)")
    fun isFavorite(itemType: String, itemId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE itemType = :itemType AND itemId = :itemId")
    suspend fun removeFavorite(itemType: String, itemId: String)

    @Query("SELECT itemId FROM favorites WHERE itemType = :itemType")
    fun getFavoriteIdsByType(itemType: String): Flow<List<String>>
}
