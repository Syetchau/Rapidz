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
        maven(url = "https://jitpack.io")

        // Wagon-git
        maven(url = "https://raw.github.com/synergian/wagon-git/releases")
    }
}

rootProject.name = "Rapidz"
include(":app")
include(":library")
