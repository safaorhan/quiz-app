plugins {
    id("base-android-library")
}


dependencies {
    implementation(project(":api"))
    implementation(Libs.Testing.mockWebServer)
    api(Libs.Testing.coroutinesTest)
}