import Module.Project.core_model

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
        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    namespace = AppConfig.namespaceDatabase

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

    // room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.viewmodel.ktx)

    // paging
    api(libs.androidx.paging.common)
    api(libs.androidx.paging.runtime)

    // hilt
    implementation(libs.stack.hilt.android)
    kapt(libs.stack.hilt.compiler)

    // test
    testImplementation(libs.stack.junit4)
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.androidx.arch.core)
    androidTestImplementation(libs.androidx.arch.core)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.stack.test.turbine)
    androidTestImplementation(libs.stack.test.coroutines)
}