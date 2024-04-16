@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("org.sonarqube") version "4.4.1.3373"
    id("java-library")
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
}

group = "games.lmdbg.rules"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "19"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
        binaries.executable()
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
