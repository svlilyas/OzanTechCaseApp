@file:Suppress("UnstableApiUsage")

import Module.Project.core_model
import com.android.build.api.dsl.LibraryBuildType

@Suppress("DSL_SCOPE_VIOLATION") plugins {
    id(libs.plugins.stack.android.library.get().pluginId)
    id(libs.plugins.stack.kotlin.android.get().pluginId)
    id(libs.plugins.stack.kotlin.kapt.get().pluginId)
    id(libs.plugins.stack.kotlin.parcelize.get().pluginId)
    id(libs.plugins.stack.hilt.plugin.get().pluginId)
    id(libs.plugins.stack.ksp.get().pluginId) version libs.versions.ksp.get()
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
    }

    buildTypes {
        debug {
            stringField(Field.SERVICE_URL to "https://api.coinranking.com/")
            stringField(Field.API_KEY to "coinrankingcce92d6fb3aca003981e8a1ef57871aad89bf1e49f054825")
        }
        release {
            stringField(Field.SERVICE_URL to "https://api.coinranking.com/")
            stringField(Field.API_KEY to "coinrankingcce92d6fb3aca003981e8a1ef57871aad89bf1e49f054825")
        }
    }

    namespace = AppConfig.namespaceNetwork

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

    implementation(libs.stack.okhttp.interceptor)

    // coroutines
    implementation(libs.stack.coroutines)

    //moshi
    implementation(libs.stack.converter.moshi)

    // network
    implementation(libs.stack.retrofit.core)

    // hilt
    implementation(libs.stack.hilt.android)
    kapt(libs.stack.hilt.compiler)
}

fun LibraryBuildType.stringField(entry: Pair<String, String>) {
    buildConfigField("String", entry.first, "\"${entry.second}\"")
}