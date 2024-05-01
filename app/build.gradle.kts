plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // Dagger Hilt Gradle Plugin
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

    // Google Services Gradle Plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "cat.copernic.abp_project_3"
    compileSdk = 34

    defaultConfig {
        applicationId = "cat.copernic.abp_project_3"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Jetpack Compose Navigation Library
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Firebase BoM Library
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    // Google Analytics Library
    implementation("com.google.firebase:firebase-analytics")
    // Google Firebase Firestore Library
    implementation("com.google.firebase:firebase-firestore")
    // Google Firebase Auth Library
    implementation("com.google.firebase:firebase-auth")
    // Firebase Storage Library
    implementation("com.google.firebase:firebase-storage")

    //Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Dagger Hilt Dependency Injection Library
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // Google Location Services
    implementation("com.google.android.gms:play-services-location:21.2.0")

    //Mapbox Library
    implementation("com.mapbox.maps:android:11.3.0")
    //Mapbox jetpack compose Extension Library
    implementation("com.mapbox.extension:maps-compose:11.3.0")

}

kapt {
    correctErrorTypes = true
}