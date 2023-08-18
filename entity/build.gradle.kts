apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(Misc.moshi)
    "implementation"(Misc.moshiKotlin)
    "implementation"(Misc.converterMoshi)
}

