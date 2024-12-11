plugins {
    id("challenges.common")
    application
}

dependencies {
    implementation(project(":std"))

    implementation("org.jgrapht:jgrapht-core:1.5.2")
}

application {
    val run = project.properties["day"] ?: return@application
    require(run is String)
    require('.' in run)

    val (year, day) = run.split('.')
    mainClass.set("com.fwdekker.aoc.y$year.Day${day}Kt")
}
