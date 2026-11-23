pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "LogicLabs-Windows"
include(":core-digital")
include(":core-bridge")
include(":core-data")
include(":core-designsystem")
include(":feature-breadboard")
include(":feature-instruments")
include(":feature-tools")
include(":core-testing")
include(":desktop-app")
