pluginManagement {
    plugins {
        kotlin("jvm") version "2.2.0"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "gradle-study"
include("app")
include("app2")
include("intermediate")
include("lib")

println("[Build LifeCycle] setting.gradle init")

includeBuild("gradle/license-plugin")
includeBuild("binary-plugin")