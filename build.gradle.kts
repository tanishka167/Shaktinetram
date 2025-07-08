// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    kotlin("jvm") version "2.1.20"
    id("com.google.gms.google-services") version "4.4.3" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    alias(libs.plugins.kotlin.android) apply false
}
