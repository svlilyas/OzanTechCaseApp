@file:Suppress("UnstableApiUsage")

import Module.Project.core_data
import Module.Project.core_database
import Module.Project.core_model
import Module.Project.core_network
import Module.Project.core_uicomponents

plugins {
    id(libs.plugins.stack.android.application.get().pluginId)
    id(libs.plugins.stack.kotlin.android.get().pluginId)
    id(libs.plugins.stack.kotlin.kapt.get().pluginId)
    id(libs.plugins.stack.kotlin.parcelize.get().pluginId)
    id(libs.plugins.stack.hilt.plugin.get().pluginId)
    id(libs.plugins.androidx.navigation.safeargs.get().pluginId)
    id(libs.plugins.stack.ksp.get().pluginId) version libs.versions.ksp.get()
}

android {
    namespace = AppConfig.namespace
    compileSdk = AppConfig.compileSdk

    signingConfigs {
        create(AppConfig.stage) {
            storeFile = file(AppConfig.demoJksFilePath)
            storePassword = AppConfig.storePassword
            keyAlias = AppConfig.keyAlias
            keyPassword = AppConfig.keyPassword
        }
        create(AppConfig.prod) {
            storeFile = file(AppConfig.demoJksFilePath)
            storePassword = AppConfig.storePassword
            keyAlias = AppConfig.keyAlias
            keyPassword = AppConfig.keyPassword
        }
    }

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = AppConfig.testInstrumentationRunner

        flavorDimensions += AppConfig.defaultFlavorDimension
    }

    buildTypes {
        debug {
        }
        release {
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    productFlavors {
        create(AppConfig.stage) {
            dimension = AppConfig.defaultFlavorDimension

            manifestPlaceholders["appLabel"] = AppConfig.devAppName

            versionName = AppConfig.devVersionName

            signingConfig = signingConfigs.getByName(AppConfig.stage)
        }
        create(AppConfig.prod) {
            dimension = AppConfig.defaultFlavorDimension

            manifestPlaceholders["appLabel"] = AppConfig.prodAppName

            versionName = AppConfig.prodVersionName

            signingConfig = signingConfigs.getByName(AppConfig.prod)
        }
    }

    packagingOptions {
        resources {
            excludes += "META-INF/services/javax.annotation.processing.Processor"
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(core_uicomponents())
    implementation(core_model())
    implementation(core_network())
    implementation(core_database())
    implementation(core_data())

    implementation(libs.androidx.core)
    implementation(libs.stack.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.ui)

    implementation(libs.stack.shimmer)
    implementation(libs.androidx.swiperefresh)
    implementation(libs.androidx.lifecycle.runtime)

    // hilt
    implementation(libs.stack.hilt.android)
    kapt(libs.stack.hilt.compiler)

    // firebase
    implementation(platform(libs.stack.firebase.bom))
    implementation(libs.stack.firebase.crashlytics)
    implementation(libs.stack.firebase.messaging)
    implementation(libs.stack.firebase.analytics)

    implementation(libs.androidx.profile.installer)

    implementation(libs.stack.mpandroid.chart)

    debugImplementation(libs.stack.leakcanary)

    // test
    testImplementation(libs.stack.test.coroutines)
    testImplementation(libs.stack.junit4)
    testImplementation(libs.stack.test.mockk)
    testImplementation(libs.androidx.arch.core)
    testImplementation(libs.stack.test.turbine)
}