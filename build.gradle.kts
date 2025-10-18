plugins {
    id("java")
}

group = "kr.co.oreb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(libs.guava)//버전 카탈로그
    implementation(libs.bundles.default)//버전 카탈로그 번들
}


tasks.test {
    useJUnitPlatform()
}

tasks.register("listTasksByPlugin") {
    group = "help"
    description = "태스크를 구현 타입의 패키지 기준으로 묶어서 출력"
    doLast {
        val byPkg = tasks.map { t ->
            val type = t.javaClass.name
            val pkg = type.substringBeforeLast('.', "(no package)")
            pkg to t
        }.groupBy({ it.first }, { it.second }).toSortedMap()

        byPkg.forEach { (pkg, ts) ->
            println("== $pkg ==")
            ts.sortedBy { it.path }.forEach { t ->
                val info = buildList {
                    t.group?.let { add("group=$it") }
                    add("type=${t.javaClass.simpleName}")
                }.joinToString()
                println("  ${t.path}  ($info)")
            }
            println()
        }
    }
}
