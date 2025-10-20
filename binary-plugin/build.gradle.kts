plugins {
    `java-gradle-plugin`
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
testing {
    suites {
        // 새로운 테스트 묶음(세트) 하나 만들기: 이름은 functionalTest
        val functionalTest by registering(JvmTestSuite::class) {

            // 이 테스트의 소스 코드가 어디 있는지 알려주기
            sources { java.setSrcDirs(listOf("src/functionalTest/java")) }
            dependencies {
                implementation(project())
                implementation(gradleTestKit())
            }
        }
    }
}

val sourceSets = the<SourceSetContainer>()

gradlePlugin {
    plugins {
        create("filesizediff") {
            id = "org.example.filesizediff"
            implementationClass = "org.example.FileSizeDiffPlugin"
        }
    }
    
    testSourceSets(sourceSets.named("functionalTest").get())
}

tasks.test {
    useJUnitPlatform()
}

