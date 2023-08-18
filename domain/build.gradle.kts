apply {
    from("$rootDir/buildSrc/android-common.gradle")
}
dependencies {
    "implementation"(project(":entity"))
    "implementation"(ArchitectureComponent.pagingCommon)
}
