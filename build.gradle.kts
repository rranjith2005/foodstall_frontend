// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    // --- ADD THIS LINE FOR KOTLIN SUPPORT ---
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    // --- END OF ADDED LINE ---

    // This plugin is required for Firebase services
    id("com.google.gms.google-services") version "4.4.2" apply false
}