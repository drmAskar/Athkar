# Athkar MVP v2 - Task Breakdown

**Version:** 2.0.0  
**Status:** Active  
**Last Updated:** 2026-03-15  
**Branch:** kotlin-mvp-step1

---

## Task Organization

Tasks are organized by phase and priority. Each task includes:
- **ID:** Unique identifier for tracking
- **Title:** Clear, actionable description
- **Priority:** P1 (Must), P2 (Should), P3 (Nice)
- **Estimate:** Time in hours
- **Dependencies:** Required prerequisites
- **Acceptance Criteria:** Definition of done
- **Status:** Not Started | In Progress | Review | Done

---

## Phase 1: Content & Core UX (P1)

### Epic: Project Foundation

#### TASK-001: Initialize Android Project
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** None  
**Status:** Not Started

**Description:**
Set up Kotlin Android project with proper structure and configuration.

**Sub-tasks:**
- [ ] Create new Android project in Android Studio
- [ ] Configure Gradle with Kotlin, Compose, Material3
- [ ] Set up package structure (Clean Architecture)
- [ ] Configure build variants (debug, release)
- [ ] Set up .gitignore for Android

**Acceptance Criteria:**
- [ ] Project builds successfully
- [ ] All dependencies are properly configured
- [ ] Package structure matches architecture plan
- [ ] README.md with project overview

---

#### TASK-002: Configure Dependencies
**Priority:** P1  
**Estimate:** 1 hour  
**Dependencies:** TASK-001  
**Status:** Not Started

**Description:**
Add all required dependencies to build.gradle.kts files.

**Dependencies to Add:**
```kotlin
// Core
androidx.core:core-ktx:1.12.0
androidx.lifecycle:lifecycle-runtime-ktx:2.7.0
androidx.activity:activity-compose:1.8.2

// Compose
androidx.compose.ui:ui
androidx.compose.material3:material3
androidx.compose.ui:ui-tooling-preview

// Navigation
androidx.navigation:navigation-compose:2.7.7

// Room
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1
androidx.room:room-compiler:2.6.1 (ksp)

// WorkManager
androidx.work:work-runtime-ktx:2.9.0

// DataStore
androidx.datastore:datastore-preferences:1.0.0

// JSON
com.google.code.gson:gson:2.10.1
```

**Acceptance Criteria:**
- [ ] All dependencies compile
- [ ] No version conflicts
- [ ] ProGuard rules configured if needed

---

#### TASK-003: Set Up Material3 Theme
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-001  
**Status:** Not Started

**Description:**
Configure Material Design 3 theme with Arabic RTL support.

**Sub-tasks:**
- [ ] Create Color.kt with app colors
- [ ] Create Type.kt with typography (include Arabic font)
- [ ] Create Theme.kt with light/dark themes
- [ ] Configure RTL support in manifest
- [ ] Add Amiri font to assets

**Acceptance Criteria:**
- [ ] Theme applies correctly to all composables
- [ ] RTL layout is forced
- [ ] Arabic fonts render properly
- [ ] Light/dark theme switching works

---

### Epic: Database Layer

#### TASK-004: Create Room Entities
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-001  
**Status:** Not Started

**Description:**
Define all Room entity classes for database schema.

**Entities to Create:**
- [ ] CategoryEntity
- [ ] AthkarEntity
- [ ] SurahEntity
- [ ] DailyProgressEntity
- [ ] FavoriteEntity
- [ ] ReminderEntity

**Acceptance Criteria:**
- [ ] All entities have proper annotations
- [ ] Foreign key relationships defined
- [ ] Indices created for query optimization
- [ ] Entity tests pass

---

#### TASK-005: Create Room DAOs
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-004  
**Status:** Not Started

**Description:**
Implement Data Access Objects for all entities.

**DAOs to Create:**
- [ ] CategoryDao (getAll, getById)
- [ ] AthkarDao (getByCategory, getAll, search, toggleFavorite)
- [ ] SurahDao (getAll, getById)
- [ ] ProgressDao (getByDate, save, getWeeklyProgress)
- [ ] FavoriteDao (getAll, add, remove, isFavorite)
- [ ] ReminderDao (getAll, save, update, delete)

**Acceptance Criteria:**
- [ ] All CRUD operations implemented
- [ ] Return types use Flow for reactive updates
- [ ] Queries use proper indices
- [ ] Transaction annotations where needed

---

#### TASK-006: Create Room Database Class
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-004, TASK-005  
**Status:** Not Started

**Description:**
Implement Room database class with migration strategy.

**Sub-tasks:**
- [ ] Create AppDatabase class
- [ ] Configure database builder
- [ ] Create migration from 0 to 1
- [ ] Add fallback to destructive migration for development
- [ ] Create DatabaseModule for DI

**Acceptance Criteria:**
- [ ] Database initializes correctly
- [ ] Migration from fresh install works
- [ ] Database inspection works in debug builds

---

### Epic: Seed Content

#### TASK-007: Create Categories Seed Data
**Priority:** P1  
**Estimate:** 1 hour  
**Dependencies:** TASK-004  
**Status:** Not Started

**Description:**
Create JSON file with category definitions.

**File:** `assets/data/categories.json`

**Content:**
```json
[
  {
    "id": "morning",
    "name_ar": "أذكار الصباح",
    "name_en": "Morning Athkar",
    "icon": "wb_sunny",
    "sort_order": 1
  },
  {
    "id": "evening",
    "name_ar": "أذكار المساء",
    "name_en": "Evening Athkar",
    "icon": "nights_stay",
    "sort_order": 2
  },
  {
    "id": "sleep",
    "name_ar": "أذكار النوم",
    "name_en": "Sleep Athkar",
    "icon": "bedtime",
    "sort_order": 3
  },
  {
    "id": "post_prayer",
    "name_ar": "أذكار بعد الصلاة",
    "name_en": "Post-Prayer Athkar",
    "icon": "mosque",
    "sort_order": 4
  }
]
```

**Acceptance Criteria:**
- [ ] JSON is valid
- [ ] All fields populated
- [ ] Arabic text verified

---

#### TASK-008: Create Morning Athkar Seed Data
**Priority:** P1  
**Estimate:** 4 hours  
**Dependencies:** TASK-007  
**Status:** Not Started

**Description:**
Compile 15+ authentic morning athkar with full metadata.

**File:** `assets/data/athkar_morning.json`

**Requirements:**
- Source from Hisnul Muslim, Sahih Al-Bukhari, Sahih Muslim
- Include full Arabic text with diacritics
- Include source reference
- Include virtues/benefits where available
- Include recommended count

**Sample Entry:**
```json
{
  "id": "morning_001",
  "category_id": "morning",
  "title_ar": "أصبحنا وأصبح الملك لله",
  "text_ar": "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ، لَا إِلَٰهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ",
  "count": 1,
  "source": "صحيح مسلم",
  "source_reference": "4/2088",
  "virtues": "من قالها كفاه الله ما أهمه من أمر الدنيا والآخرة",
  "sort_order": 1
}
```

**Acceptance Criteria:**
- [ ] Minimum 15 athkar entries
- [ ] All Arabic text verified for accuracy
- [ ] All sources properly referenced
- [ ] Sort order logical

---

#### TASK-009: Create Evening Athkar Seed Data
**Priority:** P1  
**Estimate:** 4 hours  
**Dependencies:** TASK-007  
**Status:** Not Started

**Description:**
Compile 15+ authentic evening athkar with full metadata.

**File:** `assets/data/athkar_evening.json`

**Requirements:**
- Similar structure to TASK-008
- Source from authentic hadith collections
- Proper Arabic text with diacritics

**Acceptance Criteria:**
- [ ] Minimum 15 athkar entries
- [ ] All content verified
- [ ] Consistent with morning format

---

#### TASK-010: Create Sleep Athkar Seed Data
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-007  
**Status:** Not Started

**Description:**
Compile 10+ sleep and wakeup athkar.

**File:** `assets/data/athkar_sleep.json`

**Requirements:**
- Before sleep athkar
- Upon waking athkar
- Bad dream athkar
- Sleep supplications

**Acceptance Criteria:**
- [ ] Minimum 10 athkar entries
- [ ] Covers all sub-categories
- [ ] Authentic sources

---

#### TASK-011: Create Post-Prayer Athkar Seed Data
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-007  
**Status:** Not Started

**Description:**
Compile post-prayer dhikr.

**File:** `assets/data/athkar_post_prayer.json`

**Requirements:**
- Istighfar (استغفر الله)
- Tahmeed (الحمد لله)
- Tahleel (لا إله إلا الله)
- Takbeer (الله أكبر)
- Ayat al-Kursi reference

**Acceptance Criteria:**
- [ ] Minimum 7 athkar entries
- [ ] Includes standard post-prayer dhikr
- [ ] Count recommendations included

---

#### TASK-012: Create Surahs Seed Data
**Priority:** P1  
**Estimate:** 5 hours  
**Dependencies:** None  
**Status:** Not Started

**Description:**
Compile Quran surahs with full Arabic text and metadata.

**File:** `assets/data/surahs.json`

**Required Surahs:**
1. Al-Fatiha (الفاتحة) - 7 ayat
2. Al-Ikhlas (الإخلاص) - 4 ayat
3. Al-Falaq (الفلق) - 5 ayat
4. An-Nas (الناس) - 6 ayat
5. Al-Kafirun (الكافرون) - 6 ayat
6. Ayat al-Kursi (آية الكرسي) - Baqarah:255

**Sample Entry:**
```json
{
  "id": "surah_001",
  "name_ar": "الفاتحة",
  "name_en": "Al-Fatiha",
  "surah_number": 1,
  "ayat_count": 7,
  "revelation_type": "makkah",
  "juz_number": 1,
  "text_ar": "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۝١ أَلْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ...",
  "virtues": "أم القرآن، السبع المثاني",
  "source_reference": "Quran 1:1-7"
}
```

**Acceptance Criteria:**
- [ ] All 6 surahs included
- [ ] Complete Arabic text with diacritics
- [ ] Metadata verified
- [ ] Ayat al-Kursi properly formatted

---

#### TASK-013: Implement Seed Data Loader
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-004, TASK-007 through TASK-012  
**Status:** Not Started

**Description:**
Create system to load seed data into database on first run.

**Sub-tasks:**
- [ ] Create SeedDataLoader class
- [ ] Implement JSON parsing with Gson
- [ ] Add database version check
- [ ] Insert seed data transactionally
- [ ] Handle duplicate prevention
- [ ] Add loading indicator during first run

**Acceptance Criteria:**
- [ ] Seed data loads on first launch
- [ ] Loading completes without errors
- [ ] No duplicate data on re-runs
- [ ] App remains responsive during load

---

### Epic: Repository Layer

#### TASK-014: Create Repository Interfaces
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** None  
**Status:** Not Started

**Description:**
Define repository interfaces in domain layer.

**Interfaces to Create:**
- [ ] AthkarRepository
- [ ] SurahRepository
- [ ] CategoryRepository
- [ ] ProgressRepository
- [ ] FavoriteRepository
- [ ] ReminderRepository

**Sample:**
```kotlin
interface AthkarRepository {
    fun getCategories(): Flow<List<Category>>
    fun getAthkarByCategory(categoryId: String): Flow<List<Athkar>>
    fun searchAthkar(query: String): Flow<List<Athkar>>
    suspend fun toggleFavorite(athkarId: String)
}
```

**Acceptance Criteria:**
- [ ] All interfaces defined
- [ ] Methods return appropriate types
- [ ] Located in domain/repository package

---

#### TASK-015: Implement Repository Classes
**Priority:** P1  
**Estimate:** 4 hours  
**Dependencies:** TASK-004, TASK-005, TASK-014  
**Status:** Not Started

**Description:**
Implement repository classes that use DAOs.

**Sub-tasks:**
- [ ] AthkarRepositoryImpl
- [ ] SurahRepositoryImpl
- [ ] CategoryRepositoryImpl
- [ ] ProgressRepositoryImpl
- [ ] FavoriteRepositoryImpl
- [ ] ReminderRepositoryImpl

**Acceptance Criteria:**
- [ ] All repositories implemented
- [ ] Proper error handling
- [ ] Data transformations correct
- [ ] Unit tests pass

---

### Epic: Domain Layer

#### TASK-016: Create Domain Models
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** None  
**Status:** Not Started

**Description:**
Define domain model classes.

**Models to Create:**
- [ ] Category (id, nameAr, nameEn, icon, sortOrder)
- [ ] Athkar (id, categoryId, titleAr, textAr, count, source, etc.)
- [ ] Surah (id, nameAr, nameEn, surahNumber, etc.)
- [ ] DailyProgress (date, categoryId, completedCount)
- [ ] Favorite (itemType, itemId)
- [ ] Reminder (id, type, time, enabled, days)

**Acceptance Criteria:**
- [ ] Models are data classes
- [ ] Include necessary mappings
- [ ] Immutable where possible

---

#### TASK-017: Create Use Cases
**Priority:** P1  
**Estimate:** 4 hours  
**Dependencies:** TASK-014, TASK-016  
**Status:** Not Started

**Description:**
Implement domain use cases.

**Use Cases to Create:**
- [ ] GetCategoriesUseCase
- [ ] GetAthkarByCategoryUseCase
- [ ] GetAllSurahsUseCase
- [ ] GetSurahByIdUseCase
- [ ] ToggleFavoriteUseCase
- [ ] GetFavoritesUseCase
- [ ] TrackProgressUseCase
- [ ] GetDailyProgressUseCase

**Sample:**
```kotlin
class GetAthkarByCategoryUseCase @Inject constructor(
    private val repository: AthkarRepository
) {
    operator fun invoke(categoryId: String): Flow<List<Athkar>> {
        return repository.getAthkarByCategory(categoryId)
    }
}
```

**Acceptance Criteria:**
- [ ] All use cases implemented
- [ ] Single responsibility
- [ ] Properly injected

---

### Epic: Presentation Layer - Navigation

#### TASK-018: Set Up Navigation Graph
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-001  
**Status:** Not Started

**Description:**
Configure Jetpack Navigation Compose.

**Sub-tasks:**
- [ ] Create NavGraph definition
- [ ] Define all destinations
- [ ] Create navigation actions
- [ ] Set up bottom navigation
- [ ] Configure deep links (for notifications)

**Destinations:**
- Home
- AthkarList
- AthkarDetail
- SurahList
- SurahDetail
- Favorites
- Settings

**Acceptance Criteria:**
- [ ] Navigation works between all screens
- [ ] Back stack managed correctly
- [ ] Deep links functional

---

#### TASK-019: Create Bottom Navigation
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-018  
**Status:** Not Started

**Description:**
Implement bottom navigation bar with 5 tabs.

**Tabs:**
- [ ] Home (الرئيسية) - home icon
- [ ] Athkar (الأذكار) - menu_book icon
- [ ] Surahs (السور) - auto_stories icon
- [ ] Favorites (المفضلة) - favorite icon
- [ ] Settings (الإعدادات) - settings icon

**Acceptance Criteria:**
- [ ] All tabs navigate correctly
- [ ] Active tab highlighted
- [ ] RTL layout correct

---

### Epic: Presentation Layer - Screens

#### TASK-020: Create Home Screen
**Priority:** P1  
**Estimate:** 4 hours  
**Dependencies:** TASK-017, TASK-018  
**Status:** Not Started

**Description:**
Build home dashboard with overview cards.

**Components:**
- [ ] Welcome greeting with Islamic message
- [ ] Morning progress card
- [ ] Evening progress card
- [ ] Last read continuation card
- [ ] Quick access buttons
- [ ] Daily statistics

**Acceptance Criteria:**
- [ ] Dashboard displays correctly
- [ ] Progress cards show real data
- [ ] Last read card navigates to position
- [ ] RTL layout proper

---

#### TASK-021: Create Category List Component
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-017  
**Status:** Not Started

**Description:**
Build horizontal scrolling category chips.

**Features:**
- [ ] Horizontal scrollable row
- [ ] Category chips with icons
- [ ] Selected state styling
- [ ] Click handling to filter list

**Acceptance Criteria:**
- [ ] All categories display
- [ ] Selection updates list
- [ ] Smooth scrolling

---

#### TASK-022: Create Athkar List Screen
**Priority:** P1  
**Estimate:** 4 hours  
**Dependencies:** TASK-017, TASK-021  
**Status:** Not Started

**Description:**
Build athkar list with category filtering.

**Features:**
- [ ] Top: Category chips row
- [ ] Middle: LazyColumn of athkar items
- [ ] Each item shows: title, text preview, source, favorite icon
- [ ] Click navigates to detail
- [ ] Pull to refresh (optional)

**Acceptance Criteria:**
- [ ] List displays all athkar
- [ ] Category filter works
- [ ] Favorites toggle from list
- [ ] Smooth scrolling performance

---

#### TASK-023: Create Athkar Detail Screen
**Priority:** P1  
**Estimate:** 4 hours  
**Dependencies:** TASK-017, TASK-022  
**Status:** Not Started

**Description:**
Build detailed view for single athkar.

**Features:**
- [ ] Large Arabic text display
- [ ] Title and source
- [ ] Count tracker with increment button
- [ ] Favorite toggle
- [ ] Share button
- [ ] Source reference
- [ ] Virtues section (expandable)

**Acceptance Criteria:**
- [ ] Arabic text renders beautifully
- [ ] Counter works and persists
- [ ] All metadata visible
- [ ] Share functionality works

---

#### TASK-024: Create Surah List Screen
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-017  
**Status:** Not Started

**Description:**
Build list of available surahs.

**Features:**
- [ ] List of 6 surahs
- [ ] Each shows: name, ayat count, revelation type
- [ ] Favorite icon
- [ ] Click to detail view

**Acceptance Criteria:**
- [ ] All surahs displayed
- [ ] Metadata visible
- [ ] Navigation to detail works

---

#### TASK-025: Create Surah Detail Screen
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-017, TASK-024  
**Status:** Not Started

**Description:**
Build detailed view for surah.

**Features:**
- [ ] Full Arabic text with ayat numbering
- [ ] Surah metadata header
- [ ] Virtues/benefits section
- [ ] Favorite toggle
- [ ] Share button

**Acceptance Criteria:**
- [ ] Complete surah text visible
- [ ] Proper formatting and spacing
- [ ] Ayat al-Kursi special handling

---

#### TASK-026: Create Favorites Screen
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-017  
**Status:** Not Started

**Description:**
Build favorites collection screen.

**Features:**
- [ ] Combined list of favorited items
- [ ] Type indicator (athkar vs surah)
- [ ] Remove from favorites
- [ ] Empty state message

**Acceptance Criteria:**
- [ ] All favorites displayed
- [ ] Real-time updates
- [ ] Empty state handles no favorites

---

#### TASK-027: Create Settings Screen
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** None  
**Status:** Not Started

**Description:**
Build settings screen structure (content for P2).

**Features (P1):**
- [ ] Theme toggle (light/dark/system)
- [ ] Font size adjustment
- [ ] About section
- [ ] Reminder section placeholder

**Acceptance Criteria:**
- [ ] Settings persist
- [ ] Theme changes apply immediately

---

### Epic: ViewModels

#### TASK-028: Create Home ViewModel
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-017  
**Status:** Not Started

**Description:**
Implement HomeScreen ViewModel.

**State:**
- Daily progress
- Last read position
- Recommended content

**Acceptance Criteria:**
- [ ] State flows properly
- [ ] UI updates reactively

---

#### TASK-029: Create Athkar ViewModels
**Priority:** P1  
**Estimate:** 3 hours  
**Dependencies:** TASK-017  
**Status:** Not Started

**Description:**
Implement AthkarList and AthkarDetail ViewModels.

**AthkarListViewModel:**
- Categories
- Filtered athkar list
- Selected category

**AthkarDetailViewModel:**
- Athkar details
- Counter state
- Favorite status

**Acceptance Criteria:**
- [ ] All ViewModels functional
- [ ] State management correct

---

#### TASK-030: Create Surah ViewModels
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-017  
**Status:** Not Started

**Description:**
Implement SurahList and SurahDetail ViewModels.

**Acceptance Criteria:**
- [ ] All ViewModels functional
- [ ] Proper state flow

---

### Epic: Progress Tracking

#### TASK-031: Implement Progress Tracking
**Priority:** P1  
**Estimate:** 4 hours  
**Dependencies:** TASK-015, TASK-017  
**Status:** Not Started

**Description:**
Build progress tracking system.

**Features:**
- [ ] Track daily completion per category
- [ ] Calculate percentage complete
- [ ] Persist across days
- [ ] Display on home dashboard
- [ ] Weekly streak calculation

**Acceptance Criteria:**
- [ ] Progress persists correctly
- [ ] Daily reset works
- [ ] Statistics accurate

---

#### TASK-032: Implement Last Read Tracking
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-015  
**Status:** Not Started

**Description:**
Track and resume last read position.

**Features:**
- [ ] Save last category, item, scroll position
- [ ] Update on each view
- [ ] Resume from home dashboard
- [ ] Deep link navigation

**Acceptance Criteria:**
- [ ] Last position saved
- [ ] Resume works correctly
- [ ] Handles app restart

---

### Epic: Favorites System

#### TASK-033: Implement Favorites Logic
**Priority:** P1  
**Estimate:** 2 hours  
**Dependencies:** TASK-005, TASK-015  
**Status:** Not Started

**Description:**
Build favorites toggle and retrieval.

**Features:**
- [ ] Toggle favorite status
- [ ] Get all favorites
- [ ] Check if favorited
- [ ] Remove favorite

**Acceptance Criteria:**
- [ ] Favorites persist
- [ ] Toggle updates immediately
- [ ] Cross-type favorites work

---

## Phase 2: Reminder System (P2)

### Epic: Notification Permissions

#### TASK-034: Implement Permission Request Flow
**Priority:** P2  
**Estimate:** 2 hours  
**Dependencies:** TASK-001  
**Status:** Not Started

**Description:**
Handle notification permission for Android 13+.

**Features:**
- [ ] Check notification permission
- [ ] Request permission on first launch
- [ ] Handle denial with rationale
- [ ] Settings deep link for manual enable

**Acceptance Criteria:**
- [ ] Permission flow works on Android 13+
- [ ] Graceful degradation on older versions
- [ ] User guidance provided

---

### Epic: Notification System

#### TASK-035: Create Notification Channel
**Priority:** P2  
**Estimate:** 1 hour  
**Dependencies:** None  
**Status:** Not Started

**Description:**
Set up notification channel for reminders.

**Configuration:**
- Channel ID: "athkar_reminders"
- Name: "أذكار التذكير"
- Importance: High
- Sound: Default
- Vibration: On

**Acceptance Criteria:**
- [ ] Channel created on app start
- [ ] Settings accessible

---

#### TASK-036: Create Notification Builder
**Priority:** P2  
**Estimate:** 2 hours  
**Dependencies:** TASK-035  
**Status:** Not Started

**Description:**
Build notification with proper styling.

**Features:**
- [ ] Arabic title and message
- [ ] App icon
- [ ] Deep link to content
- [ ] Action buttons (optional)

**Acceptance Criteria:**
- [ ] Notification displays correctly
- [ ] Deep link works
- [ ] RTL text proper

---

### Epic: Reminder Scheduling

#### TASK-037: Implement WorkManager Worker
**Priority:** P2  
**Estimate:** 3 hours  
**Dependencies:** TASK-035, TASK-036  
**Status:** Not Started

**Description:**
Create worker for reminder delivery.

**Features:**
- [ ] ReminderWorker class
- [ ] Build and show notification
- [ ] Handle different reminder types
- [ ] Error handling

**Acceptance Criteria:**
- [ ] Worker executes on schedule
- [ ] Notification delivered
- [ ] Handles errors gracefully

---

#### TASK-038: Implement Reminder Scheduling
**Priority:** P2  
**Estimate:** 3 hours  
**Dependencies:** TASK-037  
**Status:** Not Started

**Description:**
Schedule reminders with WorkManager.

**Features:**
- [ ] Schedule daily workers
- [ ] Calculate initial delay
- [ ] Handle time zone changes
- [ ] Reschedule on boot

**Acceptance Criteria:**
- [ ] Reminders trigger at configured time
- [ ] Survives device reboot
- [ ] Works across time zones

---

#### TASK-039: Implement Boot Receiver
**Priority:** P2  
**Estimate:** 2 hours  
**Dependencies:** TASK-038  
**Status:** Not Started

**Description:**
Reschedule reminders after device boot.

**Features:**
- [ ] BroadcastReceiver for BOOT_COMPLETED
- [ ] Permission in manifest
- [ ] Reschedule all enabled reminders

**Acceptance Criteria:**
- [ ] Reminders rescheduled on boot
- [ ] No duplicate scheduling

---

### Epic: Reminder Configuration UI

#### TASK-040: Build Reminder Settings UI
**Priority:** P2  
**Estimate:** 4 hours  
**Dependencies:** TASK-027  
**Status:** Not Started

**Description:**
Create reminder configuration section in Settings.

**Features:**
- [ ] Morning reminder toggle + time picker
- [ ] Evening reminder toggle + time picker
- [ ] Sleep reminder toggle + time picker
- [ ] Time picker dialog
- [ ] Preview of current time

**Acceptance Criteria:**
- [ ] All toggles functional
- [ ] Time pickers work
- [ ] Changes save immediately

---

#### TASK-041: Connect Reminder Settings to WorkManager
**Priority:** P2  
**Estimate:** 2 hours  
**Dependencies:** TASK-038, TASK-040  
**Status:** Not Started

**Description:**
Wire UI to scheduling system.

**Features:**
- [ ] Update schedule on toggle
- [ ] Update schedule on time change
- [ ] Cancel reminders when disabled

**Acceptance Criteria:**
- [ ] UI changes reflect in schedule
- [ ] Immediate effect

---

## Phase 3: Polish & Enhancements (P3)

### Epic: UI Polish

#### TASK-042: Implement Animations
**Priority:** P3  
**Estimate:** 4 hours  
**Dependencies:** All P1 screens  
**Status:** Not Started

**Description:**
Add smooth animations throughout app.

**Animations:**
- [ ] Screen transitions
- [ ] List item animations
- [ ] Favorite heart animation
- [ ] Counter increment animation
- [ ] Progress bar animation

**Acceptance Criteria:**
- [ ] Animations smooth (60 FPS)
- [ ] Not distracting
- [ ] Consistent throughout

---

#### TASK-043: Polish Empty States
**Priority:** P3  
**Estimate:** 2 hours  
**Dependencies:** All P1 screens  
**Status:** Not Started

**Description:**
Design and implement empty state screens.

**States:**
- [ ] No favorites
- [ ] No progress yet
- [ ] No search results
- [ ] Error states

**Acceptance Criteria:**
- [ ] Friendly messages
- [ ] Arabic text
- [ ] Consistent styling

---

### Epic: Theme & Customization

#### TASK-044: Implement Theme Selection
**Priority:** P3  
**Estimate:** 3 hours  
**Dependencies:** TASK-003  
**Status:** Not Started

**Description:**
Allow users to choose app theme.

**Features:**
- [ ] Light theme
- [ ] Dark theme
- [ ] System default
- [ ] Preview in settings

**Acceptance Criteria:**
- [ ] Theme applies immediately
- [ ] Persists across launches
- [ ] All screens themed correctly

---

#### TASK-045: Implement Font Size Adjustment
**Priority:** P3  
**Estimate:** 2 hours  
**Dependencies:** TASK-003  
**Status:** Not Started

**Description:**
Allow users to adjust text size.

**Features:**
- [ ] Font size slider
- [ ] Preview text
- [ ] Applies to all Arabic text

**Acceptance Criteria:**
- [ ] Scaling works properly
- [ ] Layout doesn't break
- [ ] Persists

---

### Epic: Performance Optimization

#### TASK-046: Profile and Optimize
**Priority:** P3  
**Estimate:** 4 hours  
**Dependencies:** All P1 and P2  
**Status:** Not Started

**Description:**
Profile app and optimize performance.

**Areas:**
- [ ] App launch time
- [ ] Database query performance
- [ ] List scroll performance
- [ ] Memory usage
- [ ] Battery optimization

**Acceptance Criteria:**
- [ ] Launch time < 2 seconds
- [ ] Smooth scrolling
- [ ] No memory leaks

---

#### TASK-047: Implement Caching Strategy
**Priority:** P3  
**Estimate:** 2 hours  
**Dependencies:** TASK-046  
**Status:** Not Started

**Description:**
Add caching for frequently accessed data.

**Features:**
- [ ] In-memory cache for categories
- [ ] Cache invalidation strategy
- [ ] Prefetch on app launch

**Acceptance Criteria:**
- [ ] Reduced database queries
- [ ] Data remains fresh

---

### Epic: Testing

#### TASK-048: Write Unit Tests
**Priority:** P3  
**Estimate:** 8 hours  
**Dependencies:** All P1 and P2  
**Status:** Not Started

**Description:**
Write comprehensive unit tests.

**Coverage:**
- [ ] Repository tests
- [ ] Use case tests
- [ ] ViewModel tests
- [ ] Utility tests

**Target:** 70% coverage

**Acceptance Criteria:**
- [ ] All tests pass
- [ ] Coverage target met
- [ ] CI integration

---

#### TASK-049: Write Integration Tests
**Priority:** P3  
**Estimate:** 4 hours  
**Dependencies:** All P1 and P2  
**Status:** Not Started

**Description:**
Write integration tests for critical flows.

**Scenarios:**
- [ ] Database operations
- [ ] Reminder scheduling
- [ ] Navigation flows
- [ ] Data persistence

**Acceptance Criteria:**
- [ ] Critical paths tested
- [ ] Tests pass reliably

---

#### TASK-050: Write UI Tests
**Priority:** P3  
**Estimate:** 4 hours  
**Dependencies:** All P1 and P2  
**Status:** Not Started

**Description:**
Write UI tests for key screens.

**Tests:**
- [ ] Navigation between screens
- [ ] Favorite toggle
- [ ] Counter functionality
- [ ] Settings changes

**Acceptance Criteria:**
- [ ] Key flows automated
- [ ] Tests stable

---

## Summary

### Total Tasks by Phase

**Phase 1 (P1):** 33 tasks  
**Phase 2 (P2):** 8 tasks  
**Phase 3 (P3):** 9 tasks

**Total:** 50 tasks

### Estimated Hours by Phase

**Phase 1 (P1):** ~100 hours  
**Phase 2 (P2):** ~21 hours  
**Phase 3 (P3):** ~31 hours

**Total:** ~152 hours (baseline)

With buffer for unexpected issues: **200 hours**

### Critical Path

```
Project Setup → Database → Seed Data → Repositories → ViewModels → Screens
                                                               ↓
                                                   Reminder System (P2)
                                                               ↓
                                                   Polish & Testing (P3)
```

---

**Document Status:** Active  
**Next Steps:** Begin TASK-001  
**Review Frequency:** Weekly during development
