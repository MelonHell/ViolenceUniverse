plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

application {
    mainClass.set("ru.melonhell.violenceuniverse.client.ViolenceUniverseClient")
}

dependencies {
    implementation(project(":common:kryo"))
}

tasks.assemble { dependsOn(tasks.shadowJar) }