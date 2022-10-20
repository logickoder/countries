plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}

@Suppress("UnstableApiUsage")
android {
    compileSdkVersion(33)

    namespace = "dev.logickoder.countries"

    defaultConfig.apply {
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        applicationId = "dev.logickoder.countries"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes.apply {
        maybeCreate("release").apply {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions.apply {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures.apply {
        compose = true
    }

    composeOptions.apply {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packagingOptions.apply {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
            jvmTarget = "11"
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.core.java8)
    implementation(libs.core)
    implementation(libs.core.appcompat)
    implementation(libs.core.material)
    // Core
    implementation(libs.core.splashscreen)
    // Accompanist - Jetpack Compose extras
    implementation(libs.accompanist.flow)
    implementation(libs.accompanist.refresh)
    implementation(libs.accompanist.placeholder)
    implementation(libs.accompanist.systemuicontroller)
    // Appyx Navigation - for navigation
    implementation(libs.appyx)
    // Coil - for loading images asynchronously
    implementation(libs.coil)
    // Compose - Jetpack Compose core libraries
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.compose.ui.test.junit)
    // JUnit - for testing
    implementation(libs.junit4)
    androidTestImplementation(libs.junit4.androidx)
    // Kotlinx immutable - immutable data type wrapper
    implementation(libs.kotlinx.immutable)
    // Kotlinx serialization - for serializing and deserializing json
    implementation(libs.kotlinx.serialization.ktor)
    implementation(libs.kotlinx.serialization.json)
    // Ktor - for network calls
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.contentnegotiation)
    // Napier - for logging
    implementation(libs.napier)
    // Preferences datastore - for cache
    implementation(libs.datastore)

    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
