plugins {
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}

dependencies {
    implementation(CoroutinesConfig.CORE)

    DaggerHiltConfig.run {
        implementation(CORE)
        kapt(COMPILER)
    }

    NetworkConfig.run {
        implementation(OKHTTP)
    }

    TestConfig.run {
        testRuntimeOnly(ENGINE)
        testImplementation(JUPITER)
        testImplementation(ASSERTJ)
        testImplementation(MOCKK)
    }
}

tasks.test {
    useJUnitPlatform()
}
