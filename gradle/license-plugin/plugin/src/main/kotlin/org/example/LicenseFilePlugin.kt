package org.example

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import java.nio.charset.StandardCharsets

class LicenseFilePlugin : Plugin<Project> {
    // Don't change anything here
    override fun apply(project: Project) {
        project.tasks.register("licenseFileTask", LicenseFileTask::class.java) { task ->
            task.description = "add a license header to source code"   // Add description
            task.group = "from license plugin"                         // Add group

            // 구성 단계에서만 기본값 주입
            task.licenseFile.set(project.layout.settingsDirectory.file("license.txt"))
            task.sourceRoot.set(project.layout.projectDirectory.dir("src"))
            task.includePattern.convention("java")
            task.dryRun.convention(false)
        }
    }
}

// 이 클래스는 Gradle "작업(Task)" 하나를 정의해요.
// 목표: 소스 코드 파일들 맨 앞에 라이선스 문구를 붙이기.
abstract class LicenseFileTask : DefaultTask() {

    // 1) 라이선스가 적혀 있는 파일을 가리키는 입력값이에요.
    //    예: 프로젝트 루트에 있는 license.txt
    //    @InputFile: "이 태스크는 어떤 파일(한 개)을 입력으로 사용해요" 라는 표시
    //    @PathSensitive(PathSensitivity.RELATIVE): 경로 비교를 "상대경로 기준"으로 해요.
    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val licenseFile: RegularFileProperty

    // 2) 소스 코드들이 있는 폴더(디렉터리)를 가리키는 입력값이에요.
    //    예: src
    //    @InputDirectory: "이 태스크는 폴더 전체를 입력으로 사용해요" 라는 표시
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val sourceRoot: DirectoryProperty

    // 3) 어떤 확장자 파일만 처리할지 정하는 문자열이에요.
    //    예: "java"면 .java 파일만 건드려요.
    @get:Input
    abstract val includePattern: Property<String>

    // 4) dryRun은 "진짜로 파일을 고칠지 말지"를 정하는 스위치예요.
    //    true  = 실제로는 안 고치고, "이렇게 할 거야" 라고 로그만 보여줌
    //    false = 진짜로 파일을 고쳐서 저장
    @get:Input
    abstract val dryRun: Property<Boolean>

    @TaskAction
    fun run() {
        // 실행 단계: 오직 프로퍼티에서 값 꺼내기
        val licenseText = licenseFile.get().asFile.readText(StandardCharsets.UTF_8)
        val root = sourceRoot.get().asFile
        val ext = includePattern.get()
        val dry = dryRun.get()

        root.walkTopDown()
            .filter { it.isFile && it.extension == ext }
            .forEach { file ->
                val content = file.readText(StandardCharsets.UTF_8)
                // 멱등성: 이미 붙어 있으면 건너뛰기
                if (content.startsWith(licenseText)) return@forEach

                if (dry) {
                    logger.lifecycle("Would prepend license: ${file.relativeTo(root)}")
                } else {
                    file.writeText(licenseText + content, StandardCharsets.UTF_8)
                    logger.info("Prepended license: ${file.relativeTo(root)}")
                }
            }
    }
}