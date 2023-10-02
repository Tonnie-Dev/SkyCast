
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
   // id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.uxstate.skycast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.uxstate.skycast"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        val openWeatherApiKey: String

        val localPropertiesFile = project.rootProject.file("local.properties")
        openWeatherApiKey = if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
            properties.getProperty("OPEN_WEATHER_API_KEY") ?: ""
        } else {
            System.getenv("OPEN_WEATHER_API_KEY") ?: ""
        }

        buildConfigField(
                "String",
                "OPEN_WEATHER_API_KEY",
                "\"$openWeatherApiKey\""
        )
        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/\"")

        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/\"")

    }

    //Kotlin Block - makes sure that the KSP Plugin looks at
    // the right paths when it comes to generated classes

    kotlin {
        sourceSets {
            debug {
                kotlin.srcDir("build/generated/ksp/debug/kotlin")
            }
            release {
                kotlin.srcDir("build/generated/ksp/release/kotlin")
            }
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
        sourceCompatibility =JavaVersion.VERSION_18
        targetCompatibility= JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion ="1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.runtime)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.compose.activity)
    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.androidx)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.junit4.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //Material 3
    implementation(libs.material3)

    // Coil
    implementation(libs.compose.coil)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.scalars)

    //Moshi Library Dependencies - Core Moshi JSON Library and Moshi")s Kotlin support and converter factory
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    // Room components
    implementation(libs.room.ktx) 
    ksp(libs.room.compiler)


    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    //Timber
    implementation(libs.timber)

    //Flow Layout
    implementation(libs.accompanist.flowlayout)

    //Lottie Animation
    implementation(libs.lottie.compose)

    // Compose Nav Destinations
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.core)
/*
    //Compose Animation
    implementation libs.compose.animation

            //Accompanist Animation
            implementation libs.accompanist.nav.animation*/

            //Maps Compose library
implementation(libs.compose.maps)
implementation(libs.play.services.maps)


}