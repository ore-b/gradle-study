plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "gradle-study"
include("app")
include("app2")
include("intermediate")

println("[Build LifeCycle] setting.gradle init")