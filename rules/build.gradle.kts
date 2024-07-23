@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("org.sonarqube") version "5.0.0.4638"
    id("java-library")
    id("org.jetbrains.kotlinx.kover") version "0.8.1"
}

group = "games.lmdbg.rules"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "16"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(LEGACY) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }

            dceTask {
                //Just because my code is not calling a function does not make it safe to delete
                keep("legendary-stats-rules.games.lmdbg.rules")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.lighthousegames:logging:1.3.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}

sonarqube {
    properties {
        property("sonar.projectKey", "knary_LegendaryStats")
        property("sonar.organization", "kon")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}