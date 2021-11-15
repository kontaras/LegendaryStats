plugins {
    kotlin("multiplatform") version "1.6.0"
    kotlin("plugin.serialization") version "1.5.31"
    id("org.sonarqube") version "3.3"
    id("java-library")
    jacoco
}

group = "games.lmdbg.rules"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17" //Kotlin does not support Java 17 yet
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(LEGACY) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
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
                api("org.lighthousegames:logging:1.0.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
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

tasks.withType<JacocoReport> {
    dependsOn("test")
    dependsOn("jvmTest")
    group = "Reporting"
    description = "Generate Jacoco coverage reports."
    val coverageSourceDirs = arrayOf(
        "commonMain/src",
        "jvmMain/src"
    )
    val classFiles = File("${buildDir}/classes/kotlin/jvm/")
        .walkBottomUp()
        .toSet()
    classDirectories.setFrom(classFiles)
    sourceDirectories.setFrom(files(coverageSourceDirs))
    additionalSourceDirs.setFrom(files(coverageSourceDirs))

    executionData
        .setFrom(files("${buildDir}/jacoco/jvmTest.exec"))

    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
        html.destination =
            File("${buildDir}/jacoco-reports/html")
    }
}
