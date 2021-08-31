plugins {
    id("base-android-library")
}

android {
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":infra:test-support"))
    implementation(project(":ui-components"))

    implementation(Libs.recyclerView)
}