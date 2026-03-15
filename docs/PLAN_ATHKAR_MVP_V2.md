# Athkar MVP v2 - Implementation Plan

**Version:** 2.0.0  
**Status:** Draft  
**Last Updated:** 2026-03-15  
**Branch:** kotlin-mvp-step1

---

## 1. Overview

This document outlines the implementation plan for Athkar MVP v2, following the specifications defined in `SPEC_ATHKAR_MVP_V2.md`. The plan is structured in three phases with clear milestones, dependencies, and deliverables.

---

## 2. Phase Breakdown

### Phase 1: Content & Core UX (P1) - Weeks 1-3
**Priority:** Must Have  
**Duration:** 3 weeks  
**Goal:** Deliver core content display, offline storage, and basic UX

### Phase 2: Reminder System (P2) - Weeks 4-5
**Priority:** Should Have  
**Duration:** 2 weeks  
**Goal:** Implement configurable notification reminders

### Phase 3: Polish & Enhancements (P3) - Week 6
**Priority:** Nice to Have  
**Duration:** 1 week  
**Goal:** UI polish, performance optimization, additional features

---

## 3. Detailed Implementation Plan

### Phase 1: Content & Core UX (Weeks 1-3)

#### Week 1: Foundation & Architecture

**Days 1-2: Project Setup**
- [ ] Initialize Kotlin Android project structure
- [ ] Configure Gradle with all dependencies
- [ ] Set up package structure (Clean Architecture)
- [ ] Configure Material Design 3 theme
- [ ] Set up Git branching strategy

**Deliverables:**
- Empty project with proper architecture
- Build configuration complete
- Navigation graph skeleton

**Days 3-4: Database Layer**
- [ ] Create Room database schema
- [ ] Implement Entity classes (Category, Athkar, Surah, etc.)
- [ ] Create DAO interfaces
- [ ] Implement Room database class
- [ ] Create database migrations strategy

**Deliverables:**
- Room database setup
- All entity classes
- Basic CRUD operations via DAOs

**Days 5-7: Data Layer**
- [ ] Create Repository interfaces
- [ ] Implement Repository implementations
- [ ] Set up DataStore for preferences
- [ ] Implement seed data loading mechanism
- [ ] Create JSON seed files structure

**Deliverables:**
- Repository pattern implementation
- Seed data infrastructure
- Data layer tests

---

#### Week 2: Content & Display

**Days 1-3: Seed Content Creation**
- [ ] Create categories.json with all 4 categories
- [ ] Create athkar_morning.json (15+ items)
- [ ] Create athkar_evening.json (15+ items)
- [ ] Create athkar_sleep.json (10+ items)
- [ ] Create athkar_post_prayer.json (7+ items)
- [ ] Create surahs.json (6 surahs with full text)
- [ ] Validate Arabic text and diacritics
- [ ] Add source references for all content

**Deliverables:**
- Complete seed data files
- Content validation report
- Asset organization

**Days 4-5: Content Loading**
- [ ] Implement seed data loader
- [ ] Create initial database population logic
- [ ] Add content versioning system
- [ ] Implement content update mechanism (future-proofing)

**Deliverables:**
- Functional seed data loader
- Database populated on first run
- Content version tracking

**Days 6-7: Category List UI**
- [ ] Create Home screen composable
- [ ] Implement bottom navigation
- [ ] Build category chips component
- [ ] Create athkar list screen
- [ ] Implement list item design
- [ ] Add loading states
- [ ] Handle empty states

**Deliverables:**
- Functional navigation
- Category display working
- List UI components

---

#### Week 3: Core Features

**Days 1-2: Athkar Detail View**
- [ ] Create athkar detail screen
- [ ] Implement Arabic text display with proper formatting
- [ ] Add expandable text functionality
- [ ] Show source metadata
- [ ] Display virtues/benefits section
- [ ] Implement count tracking

**Deliverables:**
- Complete athkar detail view
- Proper Arabic text rendering
- Count tracking functional

**Days 3-4: Surah Display**
- [ ] Create surah list screen
- [ ] Build surah detail view
- [ ] Implement surah metadata display
- [ ] Add Ayat al-Kursi special handling
- [ ] Ensure proper Quran text formatting

**Deliverables:**
- Complete surah section
- All 6 surahs accessible
- Proper formatting

**Days 5-6: Favorites & Progress**
- [ ] Implement favorites DAO methods
- [ ] Create favorites toggle logic
- [ ] Build favorites screen
- [ ] Implement daily progress tracking
- [ ] Create progress display components
- [ ] Add last-read tracking

**Deliverables:**
- Favorites system working
- Progress tracking functional
- Last-read resume feature

**Day 7: Integration Testing**
- [ ] Test all user flows
- [ ] Verify offline functionality
- [ ] Test data persistence
- [ ] Performance profiling
- [ ] Bug fixes

**Deliverables:**
- Working P1 build
- Test report
- Bug tracker populated

---

### Phase 2: Reminder System (Weeks 4-5)

#### Week 4: Notifications Foundation

**Days 1-2: Permission Handling**
- [ ] Implement notification permission check
- [ ] Create permission request flow
- [ ] Handle permission denial gracefully
- [ ] Add settings deep link for manual enable

**Deliverables:**
- Permission handling complete
- User guidance for denied permission

**Days 3-4: Notification Infrastructure**
- [ ] Create notification channel
- [ ] Design notification layout
- [ ] Implement deep link handling
- [ ] Create notification receiver

**Deliverables:**
- Notification system functional
- Deep links working

**Days 5-7: Reminder Scheduling**
- [ ] Integrate WorkManager
- [ ] Create reminder worker class
- [ ] Implement time-based scheduling
- [ ] Add reminder type differentiation
- [ ] Create reminder repository

**Deliverables:**
- Reminder scheduling functional
- Three reminder types working

---

#### Week 5: Reminder Configuration

**Days 1-3: Settings UI**
- [ ] Create Settings screen
- [ ] Build reminder configuration section
- [ ] Implement time picker dialog
- [ ] Add on/off toggles
- [ ] Create per-reminder settings
- [ ] Save preferences

**Deliverables:**
- Complete settings screen
- Reminder configuration UI

**Days 4-5: Reminder Persistence**
- [ ] Implement reminder persistence
- [ ] Handle device reboot
- [ ] Add time zone change handling
- [ ] Test across app restarts

**Deliverables:**
- Robust reminder system
- Handles edge cases

**Days 6-7: Reminder Testing**
- [ ] Test all reminder types
- [ ] Verify notification delivery
- [ ] Test permission flows
- [ ] Performance optimization
- [ ] Bug fixes

**Deliverables:**
- Working P2 build
- Reminder test report

---

### Phase 3: Polish & Enhancements (Week 6)

**Days 1-2: UI Polish**
- [ ] Refine animations
- [ ] Improve loading states
- [ ] Add error states
- [ ] Enhance RTL support
- [ ] Review UI consistency

**Deliverables:**
- Polished UI
- Smooth animations

**Days 3-4: Theme & Customization**
- [ ] Implement theme selection
- [ ] Add font size adjustment
- [ ] Create customization options
- [ ] Test accessibility

**Deliverables:**
- Theme customization
- Font size options

**Days 5-6: Performance Optimization**
- [ ] Profile app performance
- [ ] Optimize database queries
- [ ] Reduce memory usage
- [ ] Improve launch time
- [ ] Add caching strategy

**Deliverables:**
- Performance report
- Optimized build

**Day 7: Final Testing & Release Prep**
- [ ] Full regression testing
- [ ] Create release build
- [ ] Prepare release notes
- [ ] Document known issues

**Deliverables:**
- Release candidate
- Documentation complete

---

## 4. Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                       │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │  Home    │  │  Athkar  │  │  Surahs  │  │ Settings │    │
│  │  Screen  │  │  Screen  │  │  Screen  │  │  Screen  │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
│       │             │             │              │          │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              ViewModels (MVVM)                       │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                            │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              Use Cases / Interactors                   │   │
│  │  • GetCategories    • GetAthkar    • TrackProgress    │   │
│  │  • GetSurahs        • ToggleFav     • SetReminder     │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              Repository Interfaces                     │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              Domain Models                             │   │
│  │  • Category    • Athkar    • Surah    • Reminder     │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       Data Layer                             │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              Repository Implementations                │   │
│  └──────────────────────────────────────────────────────┘   │
│       │                    │                    │            │
│  ┌─────────┐        ┌─────────────┐      ┌────────────┐    │
│  │  Room   │        │  DataStore  │      │  WorkManager│    │
│  │   DB    │        │ Preferences │      │  Reminders  │    │
│  └─────────┘        └─────────────┘      └────────────┘    │
│       │                    │                                 │
│  ┌─────────┐        ┌─────────────┐                         │
│  │  Seed   │        │   Local     │                         │
│  │  Data   │        │   Storage   │                         │
│  └─────────┘        └─────────────┘                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 5. Data Flow

### 5.1 Content Loading Flow
```
App Launch
    │
    ├─► Check Database Version
    │       │
    │       ├─► If Empty: Load Seed Data
    │       │       │
    │       │       └─► Parse JSON → Insert to DB
    │       │
    │       └─► If Exists: Use Cached Data
    │
    └─► Display Content
```

### 5.2 User Interaction Flow
```
User Tap (e.g., Favorite)
    │
    ├─► ViewModel receives intent
    │       │
    │       └─► Call Use Case
    │               │
    │               └─► Repository Implementation
    │                       │
    │                       └─► DAO → Database Update
    │                               │
    │                               └─► Flow emission
    │                                       │
    └─► UI Update ◄─────────────────────────┘
```

### 5.3 Reminder Flow
```
User Configures Reminder
    │
    ├─► Save to DataStore
    │
    ├─► Schedule WorkManager Worker
    │       │
    │       └─► Worker triggers at time
    │               │
    │               └─► Build Notification
    │                       │
    │                       └─► System shows notification
    │                               │
    │                               └─► User tap → Deep Link
    │                                       │
    │                                       └─► Navigate to Content
```

---

## 6. Dependencies Between Phases

```
Phase 1 (P1) ─────────────────┐
    │                          │
    ├─► Database Schema        │
    ├─► Seed Data              │
    ├─► Content Display        │
    ├─► Favorites              │
    └─► Progress Tracking      │
                               │
                               ▼
Phase 2 (P2) ─────────────────┤
    │                          │
    ├─► Permission System      │
    ├─► Notification Channel   │
    ├─► Reminder Scheduling    │
    └─► Settings UI            │
                               │
                               ▼
Phase 3 (P3) ─────────────────┘
    │
    ├─► UI Polish
    ├─► Theme Customization
    └─► Performance Optimization
```

---

## 7. Risk Mitigation

### Risk 1: Arabic Text Rendering Issues
**Mitigation:**
- Use Amiri font consistently
- Test on multiple devices
- Validate diacritics rendering
- Fallback to system font if needed

### Risk 2: Notification Permission Denial
**Mitigation:**
- Clear permission rationale
- Easy manual enable path
- In-app guidance
- Graceful degradation

### Risk 3: Reminder Delivery Reliability
**Mitigation:**
- Use WorkManager for reliability
- Handle device reboot
- Account for battery optimization
- Test across Android versions

### Risk 4: Content Accuracy
**Mitigation:**
- Source from verified collections
- Include source references
- Community review process (future)
- Content versioning for updates

### Risk 5: Performance with Large Content
**Mitigation:**
- Pagination for lists
- Lazy loading
- Database indexing
- Caching strategy

---

## 8. Testing Strategy

### 8.1 Unit Tests
- Repository tests
- Use case tests
- ViewModel tests
- Utility function tests

**Coverage Target:** 70% minimum

### 8.2 Integration Tests
- Database operations
- Navigation flows
- Reminder scheduling
- Data persistence

### 8.3 UI Tests
- Critical user journeys
- Screen rotations
- Theme switching
- RTL layout

### 8.4 Manual Testing Checklist
- All screens on multiple devices
- Arabic text rendering
- Reminder delivery timing
- Offline functionality
- Upgrade scenarios

---

## 9. Release Checklist

### Pre-Release
- [ ] All P1 acceptance criteria met
- [ ] All P2 acceptance criteria met (if included)
- [ ] Unit tests passing
- [ ] Integration tests passing
- [ ] Manual testing complete
- [ ] Performance benchmarks met
- [ ] No critical bugs
- [ ] Release notes prepared

### Release
- [ ] Version number updated
- [ ] Changelog updated
- [ ] Screenshots updated
- [ ] Store listing updated
- [ ] APK/AAB generated
- [ ] Signed release build

### Post-Release
- [ ] Monitor crash reports
- [ ] Collect user feedback
- [ ] Plan next iteration
- [ ] Document lessons learned

---

## 10. Resource Requirements

### 10.1 Development Team
- **Android Developer (Kotlin):** 1 full-time
- **Content Researcher (Arabic):** Part-time for content validation
- **QA Tester:** Part-time for manual testing

### 10.2 Tools & Services
- Android Studio Hedgehog or newer
- Firebase Crashlytics (optional)
- GitHub Actions for CI/CD
- Figma for UI design (if needed)

### 10.3 Time Estimates
- **Phase 1:** 120 hours (3 weeks)
- **Phase 2:** 80 hours (2 weeks)
- **Phase 3:** 40 hours (1 week)
- **Total:** 240 hours (6 weeks)

---

## 11. Success Metrics

### Technical Metrics
- App launch time < 2 seconds
- Zero data loss incidents
- 99.9% reminder delivery rate
- 60 FPS scroll performance

### User Metrics
- Daily active users (DAU)
- Average session duration
- Favorite save rate
- Reminder engagement rate

### Quality Metrics
- Crash-free rate > 99%
- User rating > 4.5 stars
- Zero critical bugs
- Positive review sentiment

---

## 12. Next Steps

1. Review and approve SPEC_ATHKAR_MVP_V2.md
2. Review and approve this plan document
3. Create detailed task breakdown in TASKS_ATHKAR_MVP_V2.md
4. Set up project board with milestones
5. Begin Phase 1 implementation

---

**Document Status:** Ready for review  
**Approval Required:** Project Lead, Technical Lead  
**Next Review Date:** Upon implementation start
