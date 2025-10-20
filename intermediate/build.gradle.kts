plugins {
    id("java")
    id("application")
}

group = "kr.co.oreb"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(project(":lib"))//lib 모듈 의존성 추가
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "kr.co.oreb.App"
}

//빌드 싸이클, ./gradlew task1
println("BUILD SCRIPT: 이것은 구성 단계에서 실행됩니다")

tasks.register("task1"){
    println("REGISTER TASK1: 이것은 구성 단계에서 실행됩니다")
}

tasks.register("task2"){
    println("REGISTER TASK2: 이것은 구성 단계에서 실행됩니다")
}

tasks.named("task1"){
    println("NAMED TASK1: 이것은 구성 단계에서 실행됩니다")
    doFirst {
        println("NAMED TASK1 - doFirst: 이것은 실행 단계에서 실행됩니다")
    }
    doLast {
        println("NAMED TASK1 - doLast: 이것은 실행 단계에서 실행됩니다")
    }
}

tasks.named("task2"){
    println("NAMED TASK2: 이것은 구성 단계에서 실행됩니다")
    doFirst {
        println("NAMED TASK2 - doFirst: 이것은 실행 단계에서 실행됩니다")
    }
    doLast {
        println("NAMED TASK2 - doLast: 이것은 실행 단계에서 실행됩니다")
    }
}
