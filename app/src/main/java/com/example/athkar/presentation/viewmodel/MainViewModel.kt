package com.example.athkar.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.athkar.data.local.entities.*
import com.example.athkar.data.repository.AthkarRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HomeUiState(
    val lastReadCategory: CategoryEntity? = null,
    val lastReadItem: AthkarEntity? = null,
    val morningProgress: Float = 0f,
    val eveningProgress: Float = 0f,
    val totalDhikrCount: Int = 0
)

data class AthkarUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val selectedCategory: CategoryEntity? = null,
    val athkarList: List<AthkarEntity> = emptyList(),
    val currentAthkar: AthkarEntity? = null,
    val currentCount: Int = 0
)

data class SurahsUiState(
    val surahs: List<SurahEntity> = emptyList(),
    val selectedSurah: SurahEntity? = null
)

data class FavoritesUiState(
    val favoriteAthkar: List<AthkarEntity> = emptyList(),
    val favoriteSurahs: List<SurahEntity> = emptyList()
)

data class SettingsUiState(
    val reminders: List<ReminderEntity> = emptyList(),
    val morningReminderEnabled: Boolean = false,
    val eveningReminderEnabled: Boolean = false,
    val sleepReminderEnabled: Boolean = false
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AthkarRepository(application)
    
    // Home state
    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState: StateFlow<HomeUiState> = _homeState.asStateFlow()
    
    // Athkar state
    private val _athkarState = MutableStateFlow(AthkarUiState())
    val athkarState: StateFlow<AthkarUiState> = _athkarState.asStateFlow()
    
    // Surahs state
    private val _surahsState = MutableStateFlow(SurahsUiState())
    val surahsState: StateFlow<SurahsUiState> = _surahsState.asStateFlow()
    
    // Favorites state
    private val _favoritesState = MutableStateFlow(FavoritesUiState())
    val favoritesState: StateFlow<FavoritesUiState> = _favoritesState.asStateFlow()
    
    // Settings state
    private val _settingsState = MutableStateFlow(SettingsUiState())
    val settingsState: StateFlow<SettingsUiState> = _settingsState.asStateFlow()
    
    init {
        viewModelScope.launch {
            repository.seedDataIfNeeded()
            loadCategories()
            loadSurahs()
            loadReminders()
            loadLastRead()
            loadFavorites()
        }
    }
    
    private suspend fun loadCategories() {
        repository.getCategories().collectLatest { categories ->
            _athkarState.update { it.copy(categories = categories) }
            if (categories.isNotEmpty() && _athkarState.value.selectedCategory == null) {
                selectCategory(categories.first())
            }
        }
    }
    
    private suspend fun loadSurahs() {
        repository.getAllSurahs().collectLatest { surahs ->
            _surahsState.update { it.copy(surahs = surahs) }
        }
    }
    
    private suspend fun loadReminders() {
        repository.getAllReminders().collectLatest { reminders ->
            _settingsState.update { state ->
                state.copy(
                    reminders = reminders,
                    morningReminderEnabled = reminders.find { it.type == "morning" }?.enabled ?: false,
                    eveningReminderEnabled = reminders.find { it.type == "evening" }?.enabled ?: false,
                    sleepReminderEnabled = reminders.find { it.type == "sleep" }?.enabled ?: false
                )
            }
        }
    }
    
    private suspend fun loadLastRead() {
        repository.getLastRead().collectLatest { lastRead ->
            if (lastRead != null && lastRead.categoryId != null) {
                val category = repository.getCategories().first()
                    .find { it.id == lastRead.categoryId }
                val item = lastRead.itemId?.let { repository.getAthkarById(it) }
                _homeState.update { it.copy(lastReadCategory = category, lastReadItem = item) }
            }
        }
    }
    
    private suspend fun loadFavorites() {
        repository.getAllFavorites().collectLatest { favorites ->
            val athkarIds = favorites.filter { it.itemType == "athkar" }.map { it.itemId }
            val surahIds = favorites.filter { it.itemType == "surah" }.map { it.itemId }
            
            val allAthkar = repository.getAllAthkar().first()
            val allSurahs = repository.getAllSurahs().first()
            
            _favoritesState.update {
                it.copy(
                    favoriteAthkar = allAthkar.filter { athkar -> athkar.id in athkarIds },
                    favoriteSurahs = allSurahs.filter { surah -> surah.id in surahIds }
                )
            }
        }
    }
    
    fun selectCategory(category: CategoryEntity) {
        _athkarState.update { it.copy(selectedCategory = category, currentCount = 0) }
        viewModelScope.launch {
            repository.getAthkarByCategory(category.id).collectLatest { athkarList ->
                _athkarState.update { it.copy(athkarList = athkarList) }
            }
        }
    }
    
    fun selectAthkar(athkar: AthkarEntity) {
        _athkarState.update { it.copy(currentAthkar = athkar, currentCount = 0) }
        viewModelScope.launch {
            repository.updateLastRead(
                categoryId = _athkarState.value.selectedCategory?.id,
                itemId = athkar.id,
                itemType = "athkar"
            )
        }
    }
    
    fun incrementCount() {
        val current = _athkarState.value.currentCount
        val maxCount = _athkarState.value.currentAthkar?.count ?: 1
        if (current < maxCount) {
            _athkarState.update { it.copy(currentCount = current + 1) }
            if (current + 1 >= maxCount) {
                viewModelScope.launch {
                    _athkarState.value.selectedCategory?.id?.let { repository.incrementProgress(it) }
                }
            }
        }
    }
    
    fun resetCount() {
        _athkarState.update { it.copy(currentCount = 0) }
    }
    
    fun selectSurah(surah: SurahEntity) {
        _surahsState.update { it.copy(selectedSurah = surah) }
    }
    
    fun toggleFavorite(itemType: String, itemId: String) {
        viewModelScope.launch {
            val isFav = repository.isFavorite(itemType, itemId).first()
            if (isFav) {
                repository.removeFromFavorites(itemType, itemId)
            } else {
                repository.addToFavorites(itemType, itemId)
            }
        }
    }
    
    fun isItemFavorite(itemType: String, itemId: String): Flow<Boolean> =
        repository.isFavorite(itemType, itemId)
    
    fun toggleReminder(type: String, enabled: Boolean) {
        viewModelScope.launch {
            val reminder = _settingsState.value.reminders.find { it.type == type }
            if (reminder != null) {
                repository.updateReminder(reminder.copy(enabled = enabled))
            }
        }
    }
    
    fun updateReminderTime(type: String, time: String) {
        viewModelScope.launch {
            val reminder = _settingsState.value.reminders.find { it.type == type }
            if (reminder != null) {
                repository.updateReminder(reminder.copy(time = time))
            }
        }
    }
}
