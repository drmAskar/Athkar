package com.example.athkar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.athkar.data.local.entities.AthkarEntity
import com.example.athkar.data.local.entities.CategoryEntity
import com.example.athkar.data.local.entities.SurahEntity
import com.example.athkar.presentation.ui.screens.*
import com.example.athkar.presentation.ui.theme.AthkarTheme
import com.example.athkar.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AthkarTheme {
                AthkarApp()
            }
        }
    }
}

sealed class Screen(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    object Home : Screen("home", "الرئيسية", Icons.Default.Home)
    object Athkar : Screen("athkar", "الأذكار", Icons.Default.MenuBook)
    object Surahs : Screen("surahs", "سور", Icons.Default.Book)
    object Favorites : Screen("favorites", "المفضلة", Icons.Default.Favorite)
    object Settings : Screen("settings", "الإعدادات", Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AthkarApp() {
    val navController: NavHostController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    // Seed data on first launch
    LaunchedEffect(Unit) {
        viewModel.seedData()
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val screens = listOf(
        Screen.Home,
        Screen.Athkar,
        Screen.Surahs,
        Screen.Favorites,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title, fontSize = 12.sp) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                val homeState by viewModel.homeState.collectAsState()
                val categories by viewModel.categories.collectAsState()
                HomeScreen(
                    categories = categories,
                    lastReadCategory = homeState.lastReadCategory?.id,
                    onCategoryClick = { categoryId ->
                        viewModel.selectCategory(categoryId)
                        navController.navigate(Screen.Athkar.route)
                    }
                )
            }

            composable(Screen.Athkar.route) {
                val athkarState by viewModel.athkarState.collectAsState()
                AthkarScreen(
                    categories = athkarState.categories,
                    selectedCategory = athkarState.selectedCategory,
                    onCategorySelected = { viewModel.selectCategory(it) },
                    athkarList = athkarState.athkarList,
                    currentAthkar = athkarState.currentAthkar,
                    currentCount = athkarState.currentCount,
                    onAthkarSelected = { viewModel.selectAthkar(it) },
                    onIncrement = { viewModel.incrementCount() },
                    onReset = { viewModel.resetCount() },
                    isFavorite = { id -> viewModel.isFavorite("athkar", id) },
                    onFavoriteToggle = { viewModel.toggleFavorite("athkar", it) }
                )
            }

            composable(Screen.Surahs.route) {
                val surahsState by viewModel.surahsState.collectAsState()
                SurahsScreen(
                    surahs = surahsState.surahs,
                    isFavorite = { id -> viewModel.isFavorite("surah", id) },
                    onFavoriteToggle = { viewModel.toggleFavorite("surah", it) }
                )
            }

            composable(Screen.Favorites.route) {
                val favoritesState by viewModel.favoritesState.collectAsState()
                FavoritesScreen(
                    favorites = emptyList(),
                    favoriteAthkar = favoritesState.favoriteAthkar,
                    favoriteSurahs = favoritesState.favoriteSurahs,
                    isFavorite = { type, id -> viewModel.isFavorite(type, id) },
                    onFavoriteToggle = { type, id -> viewModel.toggleFavorite(type, id) },
                    currentCounter = viewModel.currentCounter.collectAsState().value,
                    onIncrement = { viewModel.incrementCount() },
                    onDecrement = { viewModel.incrementCount() },
                    onReset = { viewModel.resetCount() }
                )
            }

            composable(Screen.Settings.route) {
                val settingsState by viewModel.settingsState.collectAsState()
                SettingsScreen(
                    reminders = settingsState.reminders,
                    onReminderToggle = { id, enabled ->
                        viewModel.updateReminderEnabled(id, enabled)
                    },
                    onReminderTimeChange = { id, time ->
                        viewModel.updateReminderTime(id, time)
                    }
                )
            }
        }
    }
}
