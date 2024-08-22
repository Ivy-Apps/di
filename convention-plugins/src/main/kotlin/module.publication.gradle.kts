import gradle.kotlin.dsl.accessors._8edd1b0c1852f0ac869e9c414c462ba9.mavenPublishing

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    coordinates("com.ivy-apps.di", "di", "0.0.1")

    pom {
        name.set("Ivy Apps DI")
        description.set("A simple DI container for Kotlin Multiplatform apps.")
        inceptionYear.set("2024")
        url.set("https://github.com/Ivy-Apps/di")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("Ivy-Apps")
                name.set("Ivy Apps")
                url.set("https://github.com/Ivy-Apps/")
            }
        }
        scm {
            url.set("https://github.com/Ivy-Apps/di/")
            connection.set("scm:git:git://github.com/Ivy-Apps/di.git")
            developerConnection.set("scm:git:ssh://git@github.com/Ivy-Apps/di.git")
        }
    }
}
