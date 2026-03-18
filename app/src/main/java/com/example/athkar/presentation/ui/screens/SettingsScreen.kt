package com.example.athkar.presentation.ui.screens

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.athkar.data.local.entities.ReminderEntity
import kotlinx.coroutines.delay

@Composable
fun SettingsScreen(
    reminders: List<ReminderEntity>,
    onReminderToggle: (id: String, enabled: Boolean) -> Unit,
    onReminderTimeChange: (id: String, time: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    // Notification permission launcher
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Handle permission result
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(permission)
            }
        }
    }

    LaunchedEffect(reminders) {
        if (reminders.isNotEmpty()) {
            isLoading = false
        } else {
            delay(5000)
            if (reminders.isEmpty()) {
                isLoading = false
                hasError = true
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "إعدادات التنبيهات",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        when {
            isLoading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            hasError -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "لم يتم تحميل التنبيهات",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "حاول إعادة فتح التطبيق",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
            else -> {
                items(reminders, key = { it.id }) { reminder ->
                    ReminderSettingsItem(
                        reminder = reminder,
                        onEnabledChange = { enabled -> onReminderToggle(reminder.id, enabled) },
                        onTimeChange = { time -> onReminderTimeChange(reminder.id, time) }
                    )
                }
            }
        }
    }
}

@Composable
fun ReminderSettingsItem(
    reminder: ReminderEntity,
    onEnabledChange: (Boolean) -> Unit,
    onTimeChange: (String) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }

    val reminderName = when (reminder.type) {
        "morning" -> "تنبيه أذكار الصباح"
        "evening" -> "تنبيه أذكار المساء"
        "sleep" -> "تنبيه أذكار النوم"
        else -> reminder.type
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = reminderName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "الوقت: ${reminder.time}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Switch(
                    checked = reminder.enabled,
                    onCheckedChange = onEnabledChange
                )
            }
            if (reminder.enabled) {
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = { showTimePicker = true }) {
                    Text("تغيير الوقت")
                }
            }
        }
    }
    // Time picker dialog would go here
}
