plugins {
    id("base-android-library")
}

dependencies {
    api(project(":auth")) // api because otherwise dagger produces build error

    implementation(project(":ui-components"))

    testImplementation(project(":infra:test-support"))
}