import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.*
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.parcelize.plugin)
    alias(libs.plugins.ktlint)
}

android {

    namespace = "com.uxstate.skycast"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.uxstate.skycast"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        // load the values from .properties file
        val keystoreFile = project.rootProject.file("apikey.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        // return empty key in case something goes wrong
        val apiKey = properties.getProperty("API_KEY") ?: ""

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = apiKey,
        )

        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = "\"https://api.openweathermap.org/\"",
        )
    }

    buildTypes {

        release {

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            // signingConfig = signingConfigs.getByName("debug")
        }

        // TODO: check this block
        debug {
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
  /*  compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "18"
    }*/
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ktlint {
    version.set("1.3.1") // Specify the KtLint version to use
    android.set(true)
    ignoreFailures.set(true)
    reporters {

        reporter(HTML) // Output KtLint results in HTML format
    }
}

tasks.named("build") {
    dependsOn("ktlintFormat")
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
    implementation(libs.material.icons.extended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil
    implementation(libs.coil.compose)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Retrofit
    implementation(Square.retrofit2)
    implementation(Square.okHttp3)
    implementation(Square.okHttp3.loggingInterceptor)

    // Moshi Library Dependencies - Core Moshi JSON Library and Moshi"s Kotlin support and converter factory
    implementation(Square.moshi)
    implementation(Square.moshi.kotlinReflect)
    implementation(Square.retrofit2.converter.moshi)

    // Room components
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Lottie Animation
    implementation(libs.lottie.compose)

    // Compose Nav Destinations
    implementation(libs.compose.destinations.core.one)
    ksp(libs.compose.destinations.ksp.one)

    // Timber Logging
    implementation(libs.timber)

    // Splash Screen
    implementation(AndroidX.core.splashscreen)

    // Material 2 for PullRefreshIndicator
    implementation(libs.material2)

    // implementation(libs.converter.moshi)
    // implementation(libs.okhttp)
    // implementation(libs.logging.interceptor)
    // implementation(libs.converter.scalars)

    // Moshi Library Dependencies - Core Moshi JSON Library and Moshi")s Kotlin support and converter factory
    // implementation(libs.moshi)
    // implementation(libs.moshi.kotlin)

    // Compose Animation
    implementation(libs.compose.animation)

    // Accompanist - Permissions
    implementation(libs.accompanist.permissions)

    // Google Play Services
    implementation(libs.play.services.maps)

    // Data Store
    implementation(libs.data.store)

    // DesugaringLib
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}
