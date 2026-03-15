package com.example.athkar.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.athkar.data.local.database.AthkarDatabase
import com.example.athkar.data.local.entities.*
import com.example.athkar.data.repository.*
import com.example.athkar.data.seed.SeedDataLoader
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AthkarDatabase.getDatabase(application)
    
    private val categoryRepository = CategoryRepository(database.categoryDao())
    private val athkarRepository = AthkarRepository(database.athkarDao())
    private val surahRepository = SurahRepository(database.surahDao())
    private val favoriteRepository = FavoriteRepository(database.favoriteDao())
    private val userPreferenceRepository = UserPreferenceRepository(database.userPreferenceDao())
    private val reminderRepository = ReminderRepository(database.reminderDao())
    
    // UI State
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory
    
    private val _currentCounter = MutableStateFlow(0)
    val currentCounter: StateFlow<Int> = _currentCounter
    
    // Data flows
    val categories: StateFlow<List<CategoryEntity>> = categoryRepository.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val surahs: StateFlow<List<SurahEntity>> = surahRepository.getAllSurahs()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val allFavorites: StateFlow<List<FavoriteEntity>> = favoriteRepository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val reminders: StateFlow<List<ReminderEntity>> = reminderRepository.getAllReminders()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val lastReadCategory: StateFlow<String?> = userPreferenceRepository
        .getValueFlow(UserPreferenceRepository.KEY_LAST_READ_CATEGORY)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    
    val lastReadItem: StateFlow<String?> = userPreferenceRepository
        .getValueFlow(UserPreferenceRepository.KEY_LAST_READ_ITEM)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    
    fun getAthkarByCategory(categoryId: String): StateFlow<List<AthkarEntity>> {
        return athkarRepository.getAthkarByCategory(categoryId)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
    
    fun isFavorite(itemType: String, itemId: String): Flow<Boolean> {
        return favoriteRepository.isFavorite(itemType, itemId)
    }
    
    fun getFavoriteAthkar(): StateFlow<List<AthkarEntity>> {
        return favoriteRepository.getFavoriteIdsByType("athkar")
            .map { ids ->
                ids.mapNotNull { id -> database.athkarDao().getAthkarById(id) }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
    
    fun getFavoriteSurahs(): StateFlow<List<SurahEntity>> {
        return favoriteRepository.getFavoriteIdsByType("surah")
            .map { ids ->
                ids.mapNotNull { id -> database.surahDao().getSurahById(id) }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
    
    fun selectCategory(categoryId: String?) {
        _selectedCategory.value = categoryId
        categoryId?.let {
            saveLastRead(it)
        }
    }
    
    fun incrementCounter() {
        _currentCounter.value++
    }
    
    fun decrementCounter() {
        _currentCounter.value--
    }
    
    fun resetCounter() {
        _currentCounter.value = 0
    }
    
    fun completeAthkar(athkar: AthkarEntity) {
        _currentCounter.value = 0
    }
    
    fun toggleFavorite(itemType: String, itemId: String) {
        viewModelScope.launch {
            val isFav = favoriteRepository.isFavorite(itemType, itemId).first()
            if (isFav) {
                favoriteRepository.removeFavorite(itemType, itemId)
            } else {
                favoriteRepository.addFavorite(itemType, itemId)
            }
        }
    }
    
    fun saveLastRead(categoryId: String, itemId: String? = null) {
        viewModelScope.launch {
            userPreferenceRepository.setValue(
                UserPreferenceRepository.KEY_LAST_READ_CATEGORY,
                categoryId
            )
            userPreferenceRepository.setValue(
                UserPreferenceRepository.KEY_LAST_READ_TIMESTAMP,
                System.currentTimeMillis().toString()
            )
            itemId?.let {
                userPreferenceRepository.setValue(
                    UserPreferenceRepository.KEY_LAST_READ_ITEM,
                    it
                )
            }
        }
    }
    
    fun updateReminderEnabled(id: String, enabled: Boolean) {
        viewModelScope.launch {
            reminderRepository.setEnabled(id, enabled)
        }
    }
    
    fun updateReminderTime(id: String, time: String) {
        viewModelScope.launch {
            reminderRepository.setTime(id, time)
        }
    }
    
    fun seedData() {
        viewModelScope.launch {
            val seedLoader = SeedDataLoader(getApplication(), database)
            seedLoader.seedAllIfNeeded()
        }
    }
}
