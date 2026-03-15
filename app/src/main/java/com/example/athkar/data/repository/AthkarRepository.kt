package com.example.athkar.data.repository

import com.example.athkar.data.local.dao.AthkarDao
import com.example.athkar.data.local.entities.AthkarEntity
import kotlinx.coroutines.flow.Flow

class AthkarRepository(private val athkarDao: AthkarDao) {
    fun getAthkarByCategory(categoryId: String): Flow<List<AthkarEntity>> = 
        athkarDao.getAthkarByCategory(categoryId)
    
    suspend fun getAthkarById(id: String): AthkarEntity? = athkarDao.getAthkarById(id)
    
    fun getAllAthkar(): Flow<List<AthkarEntity>> = athkarDao.getAllAthkar()
    
    suspend fun insertAll(athkar: List<AthkarEntity>) = athkarDao.insertAll(athkar)
    
    suspend fun deleteAll() = athkarDao.deleteAll()
}
