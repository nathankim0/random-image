buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(KotlinConfig.GRADLE_PLUGIN)
        classpath(AndroidConfig.BUILD_TOOLS)
        classpath(DaggerHiltConfig.ANDROID_GRADLE_PLUGIN)
        classpath(FirebaseConfig.CRASHLYTICS_GRADLE)
        classpath(EtcConfig.LINT)
        classpath(TestConfig.ANDROID_JUNIT5)
        classpath(FirebaseConfig.APP_DISTRIBUTION)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
