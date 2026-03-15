# Athkar MVP v2 - Feature Specification

**Version:** 2.0.0  
**Status:** Draft  
**Last Updated:** 2026-03-15  
**Branch:** kotlin-mvp-step1

---

## 1. Executive Summary

Athkar MVP v2 transforms the existing simple dhikr counter app into a comprehensive Islamic remembrance application with organized athkar categories, common surahs, a configurable reminder system, and improved UX structure. The app follows an offline-first architecture with local storage and seeded content.

---

## 2. Core Features

### 2.1 Organized Athkar Content

#### 2.1.1 Morning Athkar (أذكار الصباح)
- **Category ID:** `morning`
- **Display Name:** أذكار الصباح
- **Target Times:** After Fajr prayer until sunrise
- **Content Structure:**
  - Title (Arabic)
  - Arabic text with full diacritics (تشكيل)
  - Count/repetition number (optional)
  - Source reference (Hadith book, page number)
  - Virtues/benefits (optional)
  - Audio recitation (optional, future)

**Minimum Content:** 15 essential morning athkar from authentic sources (Sahih Al-Bukhari, Sahih Muslim, Hisnul Muslim)

#### 2.1.2 Evening Athkar (أذكار المساء)
- **Category ID:** `evening`
- **Display Name:** أذكار المساء
- **Target Times:** After Asr prayer until Maghrib
- **Content Structure:** Same as morning athkar

**Minimum Content:** 15 essential evening athkar from authentic sources

#### 2.1.3 Sleep/Wakeup Athkar (أذكار النوم والاستيقاظ)
- **Category ID:** `sleep`
- **Display Name:** أذكار النوم والاستيقاظ
- **Sub-categories:**
  - Before sleep (أذكار قبل النوم)
  - Upon waking (أذكار الاستيقاظ)
  - Upon seeing bad dreams (أذكار عند الرؤيا السيئة)
- **Content Structure:** Same as morning athkar

**Minimum Content:** 10 sleep-related athkar

#### 2.1.4 Post-Prayer Athkar (أذكار بعد الصلاة)
- **Category ID:** `post_prayer`
- **Display Name:** أذكار بعد الصلاة
- **Applicable To:** All five daily prayers
- **Content Structure:** Same as morning athkar

**Minimum Content:** 7 post-prayer athkar (Istighfar, recommended dhikr)

---

### 2.2 Common Surahs Section

#### 2.2.1 Surah Collection
**Category ID:** `surahs`  
**Display Name:** السور القرآنية

**Included Surahs:**
1. **Al-Fatiha (الفاتحة)** - The Opening
2. **Al-Ikhlas (الإخلاص)** - The Sincerity
3. **Al-Falaq (الفلق)** - The Daybreak
4. **An-Nas (الناس)** - Mankind
5. **Al-Kafirun (الكافرون)** - The Disbelievers
6. **Ayat al-Kursi (آية الكرسي)** - The Throne Verse (from Al-Baqarah)

#### 2.2.2 Surah Content Structure
- **Arabic text:** Full surah with proper diacritics
- **Surah metadata:**
  - Surah name (Arabic & transliteration)
  - Surah number
  - Number of ayat
  - Revelation location (Makkah/Madinah)
  - Juz number
- **Source metadata:**
  - Quran reference (Surah:Ayah range)
  - Audio recitation link (optional, future)
- **Benefits/virtues:** From authentic Hadith sources

---

### 2.3 Reminder System

#### 2.3.1 Reminder Types
1. **Morning Reminder (أذكار الصباح)**
   - Default time: 05:00 (configurable)
   - Notification title: "حان وقت أذكار الصباح"
   - Deep link to morning athkar category

2. **Evening Reminder (أذكار المساء)**
   - Default time: 17:00 (configurable)
   - Notification title: "حان وقت أذكار المساء"
   - Deep link to evening athkar category

3. **Pre-Sleep Reminder (أذكار النوم)**
   - Default time: 22:00 (configurable)
   - Notification title: "أذكار قبل النوم"
   - Deep link to sleep athkar category

#### 2.3.2 Notification Structure
```
{
  "id": "athkar_reminder_{type}",
  "title": "{localized_title}",
  "body": "{localized_message}",
  "channel": "athkar_reminders",
  "priority": "high",
  "sound": "default",
  "deepLink": "athkar://category/{category_id}"
}
```

#### 2.3.3 Permissions Flow
1. First launch: Request notification permission
2. Permission denied: Show rationale with manual enable instructions
3. Settings screen: Toggle reminders on/off
4. Per-reminder configuration: Time picker for each reminder type

#### 2.3.4 Reminder Configuration
- **On/Off toggle:** Per reminder type
- **Time picker:** Custom time selection
- **Days selection:** Weekly schedule (optional, default: daily)
- **Sound selection:** Notification tone (optional, default: system)

#### 2.3.5 Technical Requirements
- Use Android AlarmManager for scheduling
- Handle device reboot (reschedule alarms)
- Handle time zone changes
- Battery optimization awareness
- WorkManager for reliable delivery

---

### 2.4 UX Structure

#### 2.4.1 Home Dashboard
**Layout:** Tab-based navigation with bottom navigation bar

**Tabs:**
1. **Home (الرئيسية)** - Dashboard overview
2. **Athkar (الأذكار)** - All athkar categories
3. **Surahs (السور)** - Quran surahs section
4. **Favorites (المفضلة)** - Bookmarked items
5. **Settings (الإعدادات)** - App configuration

**Home Dashboard Content:**
- Welcome message with Islamic greeting
- Quick access cards:
  - Morning athkar progress
  - Evening athkar progress
  - Last read position
- Daily progress counter
- Today's recommended athkar

#### 2.4.2 Category Tabs
**Athkar Screen Layout:**
- Top: Horizontal scrolling category chips
- Middle: Filterable athkar list
- Bottom: Action buttons (mark complete, favorite)

**Category Chips:**
```
[الصباح] [المساء] [النوم] [بعد الصلاة]
```

**List Item Design:**
- Arabic title (primary)
- Arabic text (truncated, expandable)
- Count badge
- Favorite icon (toggleable)
- Source badge (e.g., "صحيح البخاري")

#### 2.4.3 Progress & Counters
**Per-Session Tracking:**
- Current count for each dhikr
- Reset counter button
- Visual progress indicator (circular)

**Daily Progress:**
- Morning athkar completion percentage
- Evening athkar completion percentage
- Total dhikr count for the day

**Historical Data:**
- Weekly streak counter
- Monthly statistics (optional, P3)

#### 2.4.4 Favorites System
**Features:**
- Add/remove favorites per athkar/surah
- Dedicated favorites screen
- Quick access from home dashboard
- Persist across app reinstalls (local storage)

#### 2.4.5 Last-Read Resume
**Functionality:**
- Remember last viewed category
- Remember last viewed item
- Remember scroll position
- Deep link to last position from home dashboard

**Storage:**
```json
{
  "lastRead": {
    "categoryId": "morning",
    "itemId": "athkar_001",
    "scrollPosition": 250,
    "timestamp": "2026-03-15T10:38:00Z"
  }
}
```

---

### 2.5 Offline-First Storage

#### 2.5.1 Local Storage Architecture
**Technology:** SQLite via Room (Android) / sqflite (Flutter)

**Database Schema:**

```sql
-- Categories table
CREATE TABLE categories (
  id TEXT PRIMARY KEY,
  name_ar TEXT NOT NULL,
  name_en TEXT,
  icon TEXT,
  sort_order INTEGER DEFAULT 0
);

-- Athkar table
CREATE TABLE athkar (
  id TEXT PRIMARY KEY,
  category_id TEXT NOT NULL,
  title_ar TEXT NOT NULL,
  text_ar TEXT NOT NULL,
  count INTEGER DEFAULT 1,
  source TEXT,
  source_reference TEXT,
  virtues TEXT,
  sort_order INTEGER DEFAULT 0,
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Surahs table
CREATE TABLE surahs (
  id TEXT PRIMARY KEY,
  name_ar TEXT NOT NULL,
  name_en TEXT,
  surah_number INTEGER,
  ayat_count INTEGER,
  revelation_type TEXT,
  juz_number INTEGER,
  text_ar TEXT NOT NULL,
  virtues TEXT,
  source_reference TEXT
);

-- Progress tracking
CREATE TABLE daily_progress (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  date TEXT NOT NULL,
  category_id TEXT NOT NULL,
  completed_count INTEGER DEFAULT 0,
  total_count INTEGER,
  UNIQUE(date, category_id)
);

-- Favorites
CREATE TABLE favorites (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  item_type TEXT NOT NULL,  -- 'athkar' or 'surah'
  item_id TEXT NOT NULL,
  created_at TEXT DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(item_type, item_id)
);

-- User preferences
CREATE TABLE user_preferences (
  key TEXT PRIMARY KEY,
  value TEXT NOT NULL
);

-- Reminders
CREATE TABLE reminders (
  id TEXT PRIMARY KEY,
  type TEXT NOT NULL,
  time TEXT NOT NULL,
  enabled INTEGER DEFAULT 1,
  days TEXT  -- JSON array of days
);
```

#### 2.5.2 Seed Content Structure
**Location:** `assets/data/`

**Files:**
```
assets/
  data/
    categories.json
    athkar_morning.json
    athkar_evening.json
    athkar_sleep.json
    athkar_post_prayer.json
    surahs.json
```

**Seed Format (categories.json):**
```json
[
  {
    "id": "morning",
    "name_ar": "أذكار الصباح",
    "name_en": "Morning Athkar",
    "icon": "wb_sunny",
    "sort_order": 1
  }
]
```

**Seed Format (athkar_morning.json):**
```json
[
  {
    "id": "morning_001",
    "category_id": "morning",
    "title_ar": "أصبحنا وأصبح الملك لله",
    "text_ar": "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ...",
    "count": 1,
    "source": "صحيح مسلم",
    "source_reference": "4/2088",
    "virtues": "من قالها كفاه الله ما أهمه",
    "sort_order": 1
  }
]
```

---

## 3. Technical Specifications

### 3.1 Platform & Architecture
- **Platform:** Android (Kotlin + Jetpack Compose)
- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Architecture:** MVVM with Clean Architecture

### 3.2 Libraries & Dependencies
```kotlin
// Core
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
implementation("androidx.activity:activity-compose:1.8.2")

// Compose
implementation(platform("androidx.compose:compose-bom:2024.02.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")

// Navigation
implementation("androidx.navigation:navigation-compose:2.7.7")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// WorkManager
implementation("androidx.work:work-runtime-ktx:2.9.0")

// DataStore
implementation("androidx.datastore:datastore-preferences:1.0.0")

// JSON Parsing
implementation("com.google.code.gson:gson:2.10.1")
```

### 3.3 Package Structure
```
com.athkar.app/
├── data/
│   ├── local/
│   │   ├── database/
│   │   ├── entities/
│   │   └── dao/
│   ├── repository/
│   └── seed/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── presentation/
│   ├── ui/
│   │   ├── home/
│   │   ├── athkar/
│   │   ├── surahs/
│   │   ├── favorites/
│   │   └── settings/
│   ├── theme/
│   ├── components/
│   └── viewmodel/
└── di/
```

### 3.4 RTL Support
- Force RTL layout direction
- Arabic-first UI
- Proper text alignment
- Mirror icons for RTL

---

## 4. Acceptance Criteria

### 4.1 P1: Content & Core UX (Must Have)

**AC-P1-001: Categories Display**
- GIVEN app is launched
- WHEN user navigates to Athkar tab
- THEN all 4 categories are displayed with Arabic names
- AND categories are sorted by sort_order field

**AC-P1-002: Morning Athkar List**
- GIVEN user selects morning category
- WHEN list loads
- THEN at least 15 athkar items are displayed
- AND each item shows title, Arabic text, and source badge
- AND Arabic text is properly formatted with diacritics

**AC-P1-003: Surah Display**
- GIVEN user navigates to Surahs tab
- WHEN surah list loads
- THEN all 6 surahs are displayed
- AND each surah shows name and metadata
- AND full surah text is viewable on click

**AC-P1-004: Offline Functionality**
- GIVEN app has been launched once
- WHEN device is offline
- THEN all athkar and surah content is accessible
- AND progress tracking works offline
- AND data persists across app restarts

**AC-P1-005: Favorites**
- GIVEN user views an athkar
- WHEN user taps favorite icon
- THEN item is added to favorites
- AND item appears in Favorites tab
- AND favorite persists across sessions

**AC-P1-006: Last Read Resume**
- GIVEN user reads morning athkar
- WHEN user closes and reopens app
- THEN home dashboard shows "Continue reading" card
- AND tapping card resumes from last position

### 4.2 P2: Reminder System (Should Have)

**AC-P2-001: Notification Permission**
- GIVEN app launches for first time
- WHEN notification permission is requested
- THEN system permission dialog appears
- AND user choice is respected

**AC-P2-002: Morning Reminder**
- GIVEN morning reminder is enabled
- WHEN scheduled time arrives
- THEN notification appears with correct title
- AND tapping notification opens morning athkar

**AC-P2-003: Reminder Configuration**
- GIVEN user is in Settings
- WHEN reminder settings are displayed
- THEN each reminder type has on/off toggle
- AND each has configurable time
- AND changes are saved immediately

**AC-P2-004: Reminder Persistence**
- GIVEN reminders are configured
- WHEN device restarts
- THEN all reminders are rescheduled
- AND notifications continue to work

### 4.3 P3: Polish & Enhancements (Nice to Have)

**AC-P3-001: Progress Statistics**
- GIVEN user has used app for multiple days
- WHEN viewing progress section
- THEN weekly streak is displayed
- AND monthly statistics are shown

**AC-P3-002: Theme Customization**
- GIVEN user is in Settings
- WHEN theme options are available
- THEN user can choose light/dark/system theme
- AND theme applies immediately

**AC-P3-003: Font Size Adjustment**
- GIVEN user needs larger text
- WHEN font size setting is adjusted
- THEN Arabic text scales appropriately
- AND layout remains usable

**AC-P3-004: Audio Recitation**
- GIVEN audio is available for content
- WHEN user taps play button
- THEN audio recitation plays
- AND playback controls are visible

---

## 5. Non-Functional Requirements

### 5.1 Performance
- App launch time: < 2 seconds
- List scroll: 60 FPS minimum
- Database queries: < 100ms
- Notification delivery: < 5 seconds from scheduled time

### 5.2 Reliability
- Zero data loss on app crash
- Automatic data recovery
- Graceful degradation on errors

### 5.3 Security
- No sensitive data transmission
- Local data encryption (optional, P3)
- No analytics without consent (P3)

### 5.4 Accessibility
- Screen reader support
- Minimum touch target: 48dp
- Sufficient color contrast (WCAG AA)

---

## 6. Localization

### 6.1 Supported Languages
- **Primary:** Arabic (ar)
- **Secondary:** English (en)

### 6.2 String Resources
- All UI strings externalized
- RTL layout support
- Cultural adaptations (e.g., prayer time references)

---

## 7. Analytics & Monitoring (P3)

### 7.1 Events to Track
- App launch
- Category view
- Athkar completion
- Reminder triggered
- Reminder action (open/dismiss)

### 7.2 Crash Reporting
- Crashlytics integration
- Automatic crash reporting
- Non-PII data collection

---

## 8. Future Roadmap (Post-MVP)

### Phase 4: Advanced Features
- Prayer time integration
- Location-based reminders
- Social sharing
- Cloud sync
- Multiple user profiles

### Phase 5: Content Expansion
- Additional athkar collections
- More surahs
- Du'a categories
- Audio library

---

## 9. References

- Hisnul Muslim by Sheikh Sa'id ibn Wahf al-Qahtani
- Sahih Al-Bukhari
- Sahih Muslim
- Riyad as-Salihin
- Quran with proper tajweed

---

**Document Status:** Ready for review  
**Next Steps:** Create PLAN_ATHKAR_MVP_V2.md and TASKS_ATHKAR_MVP_V2.md
