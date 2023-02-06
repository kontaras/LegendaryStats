@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
    id("org.sonarqube") version "3.5.0.2730"
    id("java-library")
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
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
                api("org.lighthousegames:logging:1.2.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
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