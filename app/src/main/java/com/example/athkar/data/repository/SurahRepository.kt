package com.example.athkar.data.repository

import com.example.athkar.data.local.dao.SurahDao
import com.example.athkar.data.local.entities.SurahEntity
import kotlinx.coroutines.flow.Flow

class SurahRepository(private val surahDao: SurahDao) {
    fun getAllSurahs(): Flow<List<SurahEntity>> = surahDao.getAllSurahs()
    
    suspend fun getSurahById(id: String): SurahEntity? = surahDao.getSurahById(id)
    
    suspend fun insertAll(surahs: List<SurahEntity>) = surahDao.insertAll(surahs)
    
    suspend fun deleteAll() = surahDao.deleteAll()
}
