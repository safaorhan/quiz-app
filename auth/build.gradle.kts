plugins {
    id("base-android-library")
}

android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(project(":infra:worker"))

    implementation(Libs.amplifyFrameworkCoreKotlin)
    implementation(Libs.amplifyFrameworkCognito)

    coreLibraryDesugaring(Libs.desugarJdkLibs)
}
