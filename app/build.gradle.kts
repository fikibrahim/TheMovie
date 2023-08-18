plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = AndroidConfig.namespace
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":entity"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Main.kotlin)
    implementation(Main.coreKtx)
    implementation(Main.appCompat)
    implementation(Main.googleMaterial)
    implementation(Main.constraintLayout)
    implementation(Main.activityKtx)
    implementation(Main.legacy)
    implementation(Main.recyclerView)

    implementation(ArchitectureComponent.fragmentKtx)
    implementation(ArchitectureComponent.navigationFragment)
    implementation(ArchitectureComponent.livedataKtx)
    implementation(ArchitectureComponent.viewModelKtx)
    implementation(ArchitectureComponent.navigationUi)
    implementation(ArchitectureComponent.navigationFragment)
    implementation(ArchitectureComponent.coroutinesCore)
    implementation(ArchitectureComponent.coroutinesAndroid)
    implementation(ArchitectureComponent.roomPaging)
    implementation(ArchitectureComponent.paging)
    implementation(ArchitectureComponent.workManager)
    implementation(ArchitectureComponent.roomKtx)
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.compose.ui:ui-graphics-android:1.5.0")
    annotationProcessor(ArchitectureComponent.roomCompiler)
    kapt(ArchitectureComponent.roomCompiler)

    implementation(Misc.okhttp)
    implementation(Misc.loggingInterceptor)
    implementation(Misc.retrofit)
    implementation(Misc.converterMoshi)
    implementation(Misc.moshi)
    implementation(Misc.moshiKotlin)
    implementation(Misc.circularImageView)
    implementation(Misc.glide)
    kapt(Misc.glideCompiler)
    implementation(Misc.hiltAndroid)
    kapt(Misc.hiltCompiler)

    testImplementation(Testing.junit)
    androidTestImplementation(Testing.extJunit)
    androidTestImplementation(Testing.espressoCore)
}