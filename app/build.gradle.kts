plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
    id("kotlin-kapt")
}

android {

    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = "com.jinyeob.nathanks"
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(KotlinConfig.STDLIB)

    FirebaseConfig.run {
        implementation(platform(BOM))
        implementation(ANALYTICS)
        implementation(MESSAGING)
        implementation(CRASHLYTICS)
    }

    AnalyticsToolConfig.run {
        implementation(AMPLITUDE)
        implementation(KOCHAVA_TRACKER)
        implementation(KOCHAVA_TRACKER_NETWORK)
    }

    NetworkConfig.run {
        implementation(OKHTTP)
    }

    CoroutinesConfig.run {
        implementation(CORE)
        implementation(ANDROID)
    }

    DaggerHiltConfig.run {
        implementation(ANDROID)
        kapt(COMPILER)
    }

    ComposeConfig.run {
        implementation(UI)
        implementation(MATERIAL)
        implementation(UI_TOOLING)
        implementation(RUNTIME_LIVEDATA)
        implementation(ACTIVITY)
    }

    AndroidConfig.run {
        implementation(CORE)
        implementation(APPCOMPAT)
        implementation(CONSTRAINTLAYOUT)
        implementation(ACTIVITY)
        implementation(FRAGMENT)
        implementation(LEGACY_SUPPORT)
        implementation(CARD_VIEW)
        implementation(RECYCLER_VIEW)
        implementation(VIEW_PAGER)
    }

    GoogleConfig.run {
        implementation(MATERIAL)
        implementation(FLEXBOX)
        implementation(PLAY_CORE)
        implementation(PLAY_CORE_KTX)
    }

    AuthConfig.run {
//        implementation(FACEBOOK_LOGIN)
//        implementation(GOOGLE_SERVICES_AUTH)
    }

    GlideConfig.run {
        implementation(GLIDE)
        kapt(COMPILER)
    }

    NavigationConfig.run {
        implementation(FRAGMENT)
        implementation(UI)
        implementation(RUNTIME)
    }

    HiltLifecycleConfig.run {
        kapt(COMPILER)
    }

    LifecycleConfig.run {
        implementation(RUNTIME)
        implementation(LIVEDATA)
        implementation(VIEW_MODEL)
        implementation(SAVED_STATE)
    }

    IntuitConfig.run {
        implementation(SDP)
        implementation(SSP)
    }

    WorkConfig.run {
        implementation(RUNTIME)
        implementation(RUNTIME_KTX)
        implementation(HILT)
    }

    EtcConfig.run {
        implementation(EVENTBUS)
        implementation(CIRCLE_IMAGE_VIEW)
        implementation(CIRCLE_PROGRESS)
        implementation(LOTTIE)
        implementation(MP_ANDROID_CHART)
        implementation(SHIMMER)
        implementation(CAROUSEL_LAYOUT)
        implementation(PAGER_INDICATOR)
        implementation(TEDPERMISSION)
        implementation(READMORE_VIEW)
    }

    TestConfig.run {
        testRuntimeOnly(ENGINE)
        testImplementation(JUPITER)
        testImplementation(ASSERTJ)
        testImplementation(JUNIT4)
        testImplementation(TRUTH)
        testImplementation(MOCKK)
        androidTestImplementation(EXT_JUNIT)
        androidTestImplementation(ESPRESSO_CORE)
        androidTestImplementation(API)
        androidTestImplementation(ANDROID_TEST_CORE)
        androidTestRuntimeOnly(ANDROID_TEST_RUNNER)
    }
}
