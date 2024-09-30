plugins {
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jgrapht:jgrapht-core:1.5.2")

    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest:kotest-framework-datatest:5.9.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    systemProperty("kotest.framework.classpath.scanning.autoscan.disable", true)
    if (project.hasProperty("kotest.tags"))
        systemProperty("kotest.tags", project.findProperty("kotest.tags")!!)

    useJUnitPlatform {
        includeEngines("kotest")
    }
}
