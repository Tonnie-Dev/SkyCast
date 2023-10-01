// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") apply false
    id("org.jetbrains.kotlin.android") apply false
    //id ("com.google.dagger.hilt.android")  apply false
    id ("com.google.devtools.ksp") version  "1.9.10-1.0.13" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") apply false
}