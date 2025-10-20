plugins {
    id("java")
}

group = "kr.co.oreb"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("task3"){
    println("REGISTER TASK3: 이것은 구성 단계에서 실행됩니다")
}

tasks.named("task3"){
    println("NAMED TASK3: 이것은 구성 단계에서 실행됩니다")
    doFirst {
        println("NAMED TASK3 - doFirst: 이것은 실행 단계에서 실행됩니다")
    }
    doLast {
        println("NAMED TASK3 - doLast: 이것은 실행 단계에서 실행됩니다")
    }
}