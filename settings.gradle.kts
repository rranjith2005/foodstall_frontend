pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // --- THIS IS THE CORRECTED BLOCK FOR MAPBOX ---
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/private/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                // This securely reads the token from your gradle.properties file
                username = "mapbox"
                password = providers.gradleProperty("MAPBOX_DOWNLOADS_TOKEN").get()
            }
        }
    }
}

rootProject.name = "foodstall"
include(":app")