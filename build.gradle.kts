plugins {
    kotlin("jvm") version "1.7.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.9.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

application {
  mainClass.set("dev.schlaubi.aoc.LauncherKt")
}
