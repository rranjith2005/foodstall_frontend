plugins {
    // This is the correct way to apply the Android application plugin in modern Gradle
    alias(libs.plugins.android.application)
}

android {
    // Defines the package name for the app
    namespace = "com.saveetha.foodstall"

    // Sets the Android SDK version to compile against
    compileSdk = 36

    defaultConfig {
        // Sets the unique application ID
        applicationId = "com.saveetha.foodstall"

        // Minimum Android version supported
        minSdk = 24

        // Target Android version
        targetSdk = 36

        // App version code (for internal use)
        versionCode = 1

        // App version name (visible to users)
        versionName = "1.0"

        // Specifies the test runner for instrumentation tests
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Disables code shrinking for release builds
            isMinifyEnabled = false

            // Specifies ProGuard rules for release builds
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Sets the Java source and target compatibility to version 11
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Core AndroidX libraries for UI and functionality
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Using the TOML catalog, this line correctly adds the CircleImageView dependency
    implementation(libs.circleimageview)

    // Dependencies for testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}