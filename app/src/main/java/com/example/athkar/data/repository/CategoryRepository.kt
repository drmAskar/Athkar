package com.example.athkar.data.repository

import com.example.athkar.data.local.dao.CategoryDao
import com.example.athkar.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {
    fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()
    
    suspend fun getCategoryById(id: String): CategoryEntity? = categoryDao.getCategoryById(id)
    
    suspend fun insertAll(categories: List<CategoryEntity>) = categoryDao.insertAll(categories)
    
    suspend fun deleteAll() = categoryDao.deleteAll()
}
