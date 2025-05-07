import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.daggerhilt.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.ksp)
    kotlin("plugin.serialization") version "2.0.10"
    id("kotlin-parcelize")
    kotlin("kapt")
}
val keystoreProperties = Properties().apply {
    load(FileInputStream(rootProject.file("keystore.properties")))
}
val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}
android {
    namespace = "com.example.demophotosearchapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.demophotosearchapp"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val buildDate = SimpleDateFormat("ddMMyy_HHmm").format(Date())
        setProperty("archivesBaseName", "Demo_base_kotlin_build_${versionName}_${buildDate}")
//        ndk {
//            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a")) // Kotlin syntax
//        }
    }
//    externalNativeBuild {
//        cmake {
//            path ("src/main/cpp/CMakeLists.txt")
//        }
//    }
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    buildTypes {
        debug {
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions("default")
    productFlavors {
        create("dev") {
            manifestPlaceholders["appName"] = "@string/app_name_Dev"
            applicationId = "com.example.demophotosearchapp.dev"
            buildConfigField("String", "API_DOMAIN", "\"https://api.pexels.com\"")
            buildConfigField ("String", "API_KEY", "\"${localProperties.getProperty("API_KEY")}\"")
        }
        create("product") {
            manifestPlaceholders["appName"] = "@string/app_name"
            applicationId = "com.example.demophotosearchapp"
            buildConfigField("String", "API_DOMAIN", "\"https://api.pexels.com\"")
            buildConfigField ("String", "API_KEY", "\"${localProperties.getProperty("API_KEY")}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.animation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Dagger - Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    implementation(libs.androidx.multidex)
    //  chuckerteam
    debugImplementation (libs.library.v410)
    releaseImplementation (libs.library.no.op)
    //Glide
    implementation (libs.glide)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //Gson
    implementation(libs.gson)
    // Coil
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.coil.compose)

}