// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")

        val kotlin = libs.versions.kotlin.get()
        classpath(kotlin("gradle-plugin", version = kotlin))
        classpath(kotlin("serialization", version = kotlin))
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}