// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.stack.agp)
        classpath(libs.stack.kotlin.gradlePlugin)
        classpath(libs.stack.hilt.plugin)
        classpath(libs.androidx.navigation.plugin)
    }
}