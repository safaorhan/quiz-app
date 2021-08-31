plugins {
    id("base-android-library")
}

dependencies {
    implementation(project(":auth"))

    api(Libs.retrofit)
    api(Libs.converterGson)
    implementation(Libs.loggingInterceptor)
    implementation(Libs.gson)
}