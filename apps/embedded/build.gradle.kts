/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("buildlogic.java-application-conventions")
}

dependencies {
    implementation(project(":runtime"))
}

application {
    // Define the main class for the application.
    mainClass = "com.flairborne.fabulist.runtime.App"
}