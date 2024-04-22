@file:Suppress("UnstableApiUsage")

import Module.Project.core_model

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.stack.android.library.get().pluginId)
    id(libs.plugins.stack.kotlin.android.get().pluginId)
    id(libs.plugins.stack.kotlin.kapt.get().pluginId)
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
    }

    buildFeatures {
        dataBinding = true
    }

    namespace = AppConfig.namespaceUiComponents

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(core_model())

    implementation(libs.stack.coil)

    api(libs.androidx.recyclerview)
    api(libs.androidx.navigation.fragment)
    api(libs.androidx.fragment)
    api(libs.androidx.appcompat)
    implementation(libs.stack.kotlin.reflect)
}