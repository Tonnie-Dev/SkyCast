pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}



dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.3"

}

rootProject.name = "SkyCast"
include(":app")
 