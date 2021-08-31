plugins {
    id("base-android-library")
}

dependencies {
    implementation(project(":auth"))
    implementation(project(":api"))
    implementation(project(":ui-components"))

    testImplementation(project(":infra:test-support"))
}
