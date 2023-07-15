plugins {
    id("org.springframework.boot") version "3.1.1"
}

allprojects {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
    }
}

val coreProject = project("core")

// Всё суб проекты имеют доступ к core
(subprojects - coreProject).forEach { sub ->
    sub.dependencies.api(coreProject)
}

// Главный проект имеет в себе все суб проекты
subprojects.forEach { sub ->
    dependencies.implementation(sub)
}
