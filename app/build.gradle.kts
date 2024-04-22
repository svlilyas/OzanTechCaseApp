@file:Suppress("UnstableApiUsage")

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
            applicationIdSuffix = AppConfig.debugAppIdSuffix
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

            applicationIdSuffix = AppConfig.stageAppIdSuffix
            manifestPlaceholders["appLabel"] = AppConfig.devAppName

            versionName = AppConfig.devVersionName

            signingConfig = signingConfigs.getByName(AppConfig.stage)
        }
        create(AppConfig.prod) {
            dimension = AppConfig.defaultFlavorDimension

            manifestPlaceholders["appLabel"] = AppConfig.prodAppName

            versionName = AppConfig.prodVersionName

            signingConfig = signingConfigs.getByName(AppConfig.stage)
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
    implementation(libs.androidx.core)
    implementation(libs.stack.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.ui)

    implementation(libs.stack.shimmer)
    implementation(libs.androidx.swiperefresh)

    // hilt
    implementation(libs.stack.hilt.android)
    kapt(libs.stack.hilt.compiler)

    // firebase
    implementation(platform(libs.stack.firebase.bom))
    implementation(libs.stack.firebase.crashlytics)
    implementation(libs.stack.firebase.messaging)
    implementation(libs.stack.firebase.analytics)

    implementation(libs.androidx.profile.installer)

    //implementation(libs.stack.mpandroid.chart)

    debugImplementation(libs.stack.leakcanary)

    // test
    testImplementation(libs.stack.test.coroutines)
    testImplementation(libs.stack.junit4)
    testImplementation(libs.stack.test.mockk)
    testImplementation(libs.androidx.arch.core)
    testImplementation(libs.stack.test.turbine)
}