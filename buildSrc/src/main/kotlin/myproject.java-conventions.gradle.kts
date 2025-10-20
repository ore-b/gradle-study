plugins {
    id("java")
}

// 개별 테스트 작업에 대한 테스트 보고서 비활성화
tasks.named<Test>("test") {
    reports.html.required = false
}

// 전체 프로젝트에 대해 집계할 테스트 보고서 데이터를 공유합니다.
configurations.create("binaryTestResultsElements") {
    isCanBeResolved = false
    isCanBeConsumed = true
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("test-report-data"))
    }
    outgoing.artifact(tasks.test.map { task -> task.getBinaryResultsDirectory().get() })
}

repositories {
    mavenCentral()
}