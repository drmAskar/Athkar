plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.athkar.athkar"
    compileSdk = flutter.compileSdkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        applicationId = "com.athkar.athkar"
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        // Use CI build number for monotonically increasing versionCode
        // Fallback to flutter versionCode if not in CI
        versionCode = System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: flutter.versionCode()
        versionName = flutter.versionName()
    }

    // Disable all splits for universal APK (single APK for all devices)
    splits {
        abi {
            isEnable = false
        }
        density {
            isEnable = false
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            // Debug builds are installable from scratch
            isDebuggable = true
        }
    }
}

flutter {
    source = "../.."
}
