println("[Build LifeCycle] build.gradle init")

plugins {
    application
    //gradle 에서 관리하는, 메이븐 배포 플러그인
    id("maven-publish")
}

repositories {
    mavenCentral()
}

dependencies {
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.junit.jupiter)//버전 카탈로그
    implementation(libs.guava)//버전 카탈로그
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

    doLast {
        println("[Build LifeCycle] test task")
    }

    finalizedBy("testBoth")

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

publishing {
    publications {
        //“퍼블리케이션을 하나라도 정의”하면
        // 그 퍼블리케이션을 만들고 배포하기 위한 하위 태스크들을 자동으로 생성
        create<MavenPublication>("maven") {
            //메이븐 설정값 지정
            groupId = "com.gradle.tutorial"
            artifactId = "tutorial"
            version = "1.0"

            from(components["java"])//java 플러그인이 만들어주는 기본 산출물들을 포함
        }
    }
}


tasks.register("configured") {
    println("[Build LifeCycle] configured task")
}


tasks.register("testBoth") {
    doFirst {
        println("[Build LifeCycle] testBoth task first")
    }
    doLast {
        println("[Build LifeCycle] testBoth task last")
    }
    println("[Build LifeCycle] testBoth task init")
}