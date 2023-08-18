apply {
    from("$rootDir/buildSrc/android-common.gradle")
}
dependencies {
    "implementation"(project(":entity"))
    "implementation"(project(":domain"))
    "implementation"(ArchitectureComponent.coroutinesCore)
    "implementation"(ArchitectureComponent.coroutinesAndroid)
    "implementation"(ArchitectureComponent.pagingCommon)
    "implementation"(Misc.okhttp)
    "implementation"(Misc.loggingInterceptor)
    "implementation"(Misc.retrofit)
}