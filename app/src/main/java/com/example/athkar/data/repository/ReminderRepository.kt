package com.example.athkar.data.repository

import com.example.athkar.data.local.dao.ReminderDao
import com.example.athkar.data.local.entities.ReminderEntity
import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val reminderDao: ReminderDao) {
    fun getAllReminders(): Flow<List<ReminderEntity>> = reminderDao.getAllReminders()
    
    suspend fun getReminderById(id: String): ReminderEntity? = reminderDao.getReminderById(id)
    
    suspend fun upsert(reminder: ReminderEntity) = reminderDao.upsert(reminder)
    
    suspend fun setEnabled(id: String, enabled: Boolean) = reminderDao.setEnabled(id, enabled)
    
    suspend fun setTime(id: String, time: String) = reminderDao.setTime(id, time)
    
    companion object {
        const val MORNING_ID = "morning"
        const val EVENING_ID = "evening"
        const val SLEEP_ID = "sleep"
        
        const val DEFAULT_MORNING_TIME = "05:00"
        const val DEFAULT_EVENING_TIME = "17:00"
        const val DEFAULT_SLEEP_TIME = "22:00"
    }
}
