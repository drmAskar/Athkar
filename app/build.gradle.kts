plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version ("1.9.0-1.0.13")
}

android {
    namespace = "com.example.athkar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.athkar"
        minSdk = 24
        targetSdk = 34
        // Version code from CI environment or fallback to 1
        // GITHUB_RUN_NUMBER provides monotonically increasing builds
        versionCode = (System.getenv("GITHUB_RUN_NUMBER")?.toIntOrNull() ?: 1)
        versionName = "1.0.${System.getenv("GITHUB_RUN_NUMBER") ?: "1"}"
    }

    // Disable ABI splits for universal APK (single APK for all devices)
    splits {
        abi {
            isEnable = false
        }
        density {
            isEnable = false
        }
    }

    // Ensure single universal APK output
    bundle {
        language {
            // Disable language split for universal APK
            enableSplit = false
        }
        density {
            // Disable density split for universal APK
            enableSplit = false
        }
        abi {
            // Disable ABI split for universal APK
            enableSplit = false
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
        debug {
            // Debug builds are installable from scratch
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")

    // Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.code.gson:gson:2.10.1")
}
