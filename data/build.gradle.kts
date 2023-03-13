plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {

    compileSdkVersion(AppConfig.compileSdk)

    defaultConfig {
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}

dependencies {
    implementation(project(":domain"))

    implementation(CoroutinesConfig.CORE)

    DaggerHiltConfig.run {
        implementation(ANDROID)
        kapt(COMPILER)
    }

    NetworkConfig.run {
        api(RETROFIT)
        api(CONVERTER_MOSHI)
        implementation(LOGGING_INTERCEPTOR)
        implementation(GSON)
        implementation(CONVERTER_GSON)
    }

    RoomConfig.run {
        implementation(KTX)
        api(RUNTIME)
        kapt(COMPILER)
        testImplementation(TESTING)
    }

    ConverterConfig.run {
        api(MOSHI_KOTLIN)
        kapt(MOSHI_KOTLIN_CODEGEN)
    }

    TestConfig.run {
        testRuntimeOnly(ENGINE)
        testImplementation(JUPITER)
        testImplementation(ASSERTJ)
        testImplementation(MOCKK)
        testImplementation(MOCK_WEB_SERVER)
        testImplementation(EXT_JUNIT)
    }
}
