
plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.example.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
//예시 1: build/libs 밑 .war를 target으로 복사하고 싶다
tasks.register<Copy>("copyTask") {
    from("build/libs")
    into("target")
    include("**/*.war")   // 하위 폴더까지 .war 전부
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


tasks.register("hello") {
    doLast {                      // 태스크 실행 단계의 '마지막에' 할 일 추가
        println("Hello!")
    }
}

tasks.register("greet") {
    doLast {
        println("How are you?")
    }
    dependsOn("hello")            // greet 실행 전에 hello 반드시 실행
}