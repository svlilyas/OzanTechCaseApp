import Module.Project.core_database
import Module.Project.core_model
import Module.Project.core_network

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
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

    namespace = AppConfig.namespaceData

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
    implementation(core_network())
    implementation(core_database())

    // coroutines
    implementation(libs.stack.coroutines)

    // network
    implementation(libs.stack.retrofit.core)

    // room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // hilt
    implementation(libs.stack.hilt.android)
    kapt(libs.stack.hilt.compiler)

    // test
    testImplementation(libs.stack.test.coroutines)
    testImplementation(libs.stack.junit4)
    testImplementation(libs.stack.test.mockk)
    testImplementation(libs.androidx.arch.core)
    testImplementation(libs.stack.test.turbine)
}