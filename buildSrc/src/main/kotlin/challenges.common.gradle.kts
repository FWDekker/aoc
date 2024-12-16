plugins {
    id("org.jetbrains.kotlin.jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.20")

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

// Export test fixtures for other projects, see https://stackoverflow.com/a/61682321/
configurations {
    create("test")
}

tasks.register<Jar>("testArchive") {
    archiveBaseName.set("std-test")
    from(project.the<SourceSetContainer>()["test"].output)
}

artifacts {
    add("test", tasks["testArchive"])
}
