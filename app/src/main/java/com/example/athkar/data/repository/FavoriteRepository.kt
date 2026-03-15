package com.example.athkar.data.repository

import com.example.athkar.data.local.dao.FavoriteDao
import com.example.athkar.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun getAllFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()
    
    fun isFavorite(itemType: String, itemId: String): Flow<Boolean> = 
        favoriteDao.isFavorite(itemType, itemId)
    
    suspend fun addFavorite(itemType: String, itemId: String) = 
        favoriteDao.addFavorite(FavoriteEntity(itemType = itemType, itemId = itemId))
    
    suspend fun removeFavorite(itemType: String, itemId: String) = 
        favoriteDao.removeFavorite(itemType, itemId)
    
    fun getFavoriteIdsByType(itemType: String): Flow<List<String>> = 
        favoriteDao.getFavoriteIdsByType(itemType)
}
