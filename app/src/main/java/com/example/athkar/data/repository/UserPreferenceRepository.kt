package com.example.athkar.data.repository

import com.example.athkar.data.local.dao.UserPreferenceDao
import com.example.athkar.data.local.entities.UserPreferenceEntity
import kotlinx.coroutines.flow.Flow

class UserPreferenceRepository(private val preferenceDao: UserPreferenceDao) {
    suspend fun getValue(key: String): String? = preferenceDao.getValue(key)
    
    fun getValueFlow(key: String): Flow<String?> = preferenceDao.getValueFlow(key)
    
    suspend fun setValue(key: String, value: String) = 
        preferenceDao.setValue(UserPreferenceEntity(key, value))
    
    suspend fun delete(key: String) = preferenceDao.delete(key)
    
    companion object {
        const val KEY_LAST_READ_CATEGORY = "last_read_category"
        const val KEY_LAST_READ_ITEM = "last_read_item"
        const val KEY_LAST_READ_TIMESTAMP = "last_read_timestamp"
    }
}
