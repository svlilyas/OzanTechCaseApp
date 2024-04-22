@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.stack.android.library.get().pluginId)
    id(libs.plugins.stack.kotlin.android.get().pluginId)
    id(libs.plugins.stack.kotlin.parcelize.get().pluginId)
    id(libs.plugins.stack.ksp.get().pluginId) version libs.versions.ksp.get()
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
    }

    buildTypes {
        release {
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    namespace = AppConfig.namespaceModel

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    // Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    api(libs.stack.timber)

    // Moshi
    api(libs.stack.moshi.kotlin)

    //Gson
    implementation(libs.stack.gson)
}