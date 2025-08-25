plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.saveetha.foodstall"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.saveetha.foodstall"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Core AndroidX libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.circleimageview)

    // OpenStreetMap (osmdroid) dependency - CORRECTED with double quotes
    implementation("org.osmdroid:osmdroid-android:6.1.18")

    // Google Play Services Location for getting the user's location
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}