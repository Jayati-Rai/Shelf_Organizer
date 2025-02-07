// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    repositories {
        google() // Android-specific libraries and tools
        mavenCentral() // General-purpose dependencies
    }
    dependencies {
        // Google Services plugin (for Firebase integration)
        classpath(libs.google.services) // Replace with the latest version if available

        // Kotlin Gradle plugin
        classpath(libs.kotlin.gradle.plugin) // Ensure the version matches your Kotlin version

        // Add other classpath as needed for plugins
    }
}


