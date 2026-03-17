# Athkar Android App - Installation Guide

This document provides comprehensive installation instructions and troubleshooting for the Athkar Android application.

## Quick Installation

1. Download the APK artifact from [GitHub Actions](https://github.com/drmAskar/Athkar/actions)
2. Extract the ZIP archive
3. Transfer `athkar-installable.apk` to your Android device
4. Open the APK on your device to install

## System Requirements

- **Android Version:** 7.0 (API 24) or higher
- **Storage:** ~20 MB free space
- **Permissions:** Location permission (optional, for certain features)

## Installation Methods

### Method 1: Direct Installation (Recommended)

1. Download the `athkar-installable-apk` artifact from GitHub Actions
2. Unzip the downloaded archive
3. Copy `athkar-installable.apk` to your device via USB, cloud storage, or direct download
4. On your device, navigate to the APK file using a file manager
5. Tap the APK and follow the installation prompts

### Method 2: ADB Installation (For Developers)

```bash
# Enable USB debugging on your device first
adb install athkar-installable.apk

# If previous version exists:
adb uninstall com.example.athkar
adb install athkar-installable.apk
```

### Method 3: Emulator Installation

```bash
# Start emulator
emulator -avd <avd_name> &

# Install APK
adb install athkar-installable.apk
```

## Pre-Installation Checklist

- [ ] Unknown sources enabled: Settings → Security → "Install from unknown sources" (or "Install unknown apps" per-app on newer Android)
- [ ] Sufficient storage space available
- [ ] Previous version uninstalled (if upgrading from earlier builds)

## Troubleshooting Table

| Error Message | German Message | Cause | Solution |
|---------------|----------------|-------|----------|
| `INSTALL_FAILED_ALREADY_EXISTS` | App bereits vorhanden | App with same package already installed | Uninstall existing app first |
| `INSTALL_FAILED_UPDATE_INCOMPATIBLE` | Aktualisierung nicht kompatibel | Signature mismatch with existing app | Uninstall old version completely, then install new |
| `INSTALL_FAILED_INVALID_APK` | Ungültiges APK | Corrupted APK file | Re-download the artifact; verify ZIP extraction |
| `INSTALL_FAILED_INSUFFICIENT_STORAGE` | Nicht genügend Speicherplatz | Device storage full | Free up storage space (delete unused apps/files) |
| `INSTALL_FAILED_VERSION_DOWNGRADE` | Versions-Downgrade nicht möglich | Newer version already installed | Uninstall existing app before installing older version |
| `INSTALL_PARSE_FAILED_NO_CERTIFICATES` | Keine Zertifikate | APK signing issue | Re-download artifact; ensure proper extraction |
| `INSTALL_PARSE_FAILED_MANIFEST_MALFORMED` | Manifest-Fehler | Corrupted APK | Re-download and extract properly |
| `INSTALL_FAILED_BLOCKED_BY_POLICY` | Durch Richtlinie blockiert | Device admin/MDM restriction | Check with IT admin; disable device admin if personal device |
| `INSTALL_FAILED_RESTRICTED_INSTALLER` | Installation eingeschränkt | Restricted installer source | Enable "Allow from this source" for your browser/file manager |
| "App not installed" (generic) | "App nicht installiert" | Various causes | Enable unknown sources; check storage; uninstall old version |
| "Nicht installiert" | "Nicht installiert" | Various causes | Follow same steps as "App not installed" |
| "Installation blocked" | "Installation blockiert" | Unknown sources disabled | Settings → Allow from this source |
| `INSTALL_FAILED_TEST_ONLY` | Nur für Testzwecke | Test-only APK restriction | Use `adb install -t athkar-installable.apk` |

## Common Issues & Solutions

### "Install blocked" / "Install unknown apps"

**Android 8.0+ (Oreo and later):**
1. Go to Settings → Apps → Special app access → Install unknown apps
2. Select your browser or file manager
3. Enable "Allow from this source"

**Android 7.x and earlier:**
1. Go to Settings → Security
2. Enable "Unknown sources"

### Signature Mismatch Issues

If you receive "INSTALL_FAILED_UPDATE_INCOMPATIBLE" or signature errors:

```bash
# Remove existing app completely
adb uninstall com.example.athkar

# Verify removal
adb shell pm list packages | grep athkar
# (should return nothing)

# Install fresh
adb install athkar-installable.apk
```

### Storage Issues

If "INSTALL_FAILED_INSUFFICIENT_STORAGE":
1. Clear cache: Settings → Storage → Cached data
2. Remove unused apps
3. Move media to SD card or cloud storage
4. Try installing again

### Device Administrator Issues

If "INSTALL_FAILED_BLOCKED_BY_POLICY":
1. Check if device is enrolled in MDM (work profile, etc.)
2. Settings → Security → Device admin apps
3. Disable any active device admins (if personal device)
4. Or contact IT admin for work devices

## Verification

After installation, verify the app is properly installed:

```bash
# List installed packages
adb shell pm list packages | grep athkar

# Expected output:
# package:com.example.athkar

# Get version info
adb shell dumpsys package com.example.athkar | grep versionName
```

## Uninstallation

### Via Device Settings
1. Settings → Apps → Athkar
2. Tap "Uninstall"

### Via ADB
```bash
adb uninstall com.example.athkar
```

## Getting Help

If you continue to experience installation issues:

1. Check [GitHub Issues](https://github.com/drmAskar/Athkar/issues) for known problems
2. Include in your report:
   - Device model and Android version
   - Exact error message (screenshot preferred)
   - Installation method used
   - Whether previous versions were installed

## Build Information

- **Package Name:** `com.example.athkar`
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Build Type:** Debug (signed with debug key)
