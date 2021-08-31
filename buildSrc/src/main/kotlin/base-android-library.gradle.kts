plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-allopen")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    flavorDimensions += listOf("env")

    productFlavors {
        create("dev") {
            dimension = "env"
        }

        create("qa") {
            dimension = "env"
        }

        create("prod") {
            dimension = "env"
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures.dataBinding = true

    kotlinOptions {
        jvmTarget = "1.8"

        freeCompilerArgs += listOf(
            "-Xopt-in=kotlin.time.ExperimentalTime",
        )
    }
}

allOpen {
    annotation("com.hoopow.apps.infra.base.OpenForTesting")
}

dependencies {
    if (name != "base") implementation(project(":infra:base"))

    // Repeated in app and in base-android-library.kts)
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltCompiler)

    testImplementation(Libs.Testing.junit)
    testImplementation(Libs.Testing.assertJ)
    testImplementation(Libs.Testing.mockitoKotlin)
    testImplementation(Libs.Testing.mockWebServer)
    testImplementation(Libs.Testing.turbine)

    androidTestImplementation(Libs.Testing.extJunit)
    androidTestImplementation(Libs.Testing.espressoCore)
}