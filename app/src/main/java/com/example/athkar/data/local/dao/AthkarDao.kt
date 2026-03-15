package com.example.athkar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athkar.data.local.entities.AthkarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AthkarDao {
    @Query("SELECT * FROM athkar WHERE categoryId = :categoryId ORDER BY sortOrder ASC")
    fun getAthkarByCategory(categoryId: String): Flow<List<AthkarEntity>>

    @Query("SELECT * FROM athkar WHERE id = :id")
    suspend fun getAthkarById(id: String): AthkarEntity?

    @Query("SELECT * FROM athkar ORDER BY categoryId, sortOrder ASC")
    fun getAllAthkar(): Flow<List<AthkarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(athkar: List<AthkarEntity>)

    @Query("DELETE FROM athkar")
    suspend fun deleteAll()
}
