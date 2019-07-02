import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.4.1/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java
    kotlin("jvm") version "1.3.41"

    // Apply the application plugin to add support for building an application
    application
}

repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")
    implementation("com.google.guava:guava:27.0.1-jre")
    implementation("org.apache.kafka:kafka-clients:2.2.0")
    implementation("org.apache.logging.log4j:log4j-core:2.11.2")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.11.2")
    implementation("org.zeroturnaround:zt-zip:1.13")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-M2")
    implementation("org.redisson:redisson:3.11.1")

    implementation("org.eclipse.rdf4j:rdf4j-runtime:2.5.2") {
        exclude(group = "ch.qos.logback", module = "logback-classic")
    }
    implementation("org.eclipse.rdf4j:rdf4j-rio:2.5.2")
    // Fixes undefined HexBinaryAdapter in org.eclipse.rdf4j.rio.helpers::AbstractRDFParser
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("com.github.jsonld-java:jsonld-java:0.12.4")

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    // Use JUnit test framework
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("com.puppycrawl.tools:checkstyle:8.21")
}

application {
    // Define the main class for the application
    mainClassName = "io.ontola.ori.api.OriApiKt"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}