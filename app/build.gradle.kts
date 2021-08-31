plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Config.compileSdk
    buildToolsVersion = Config.buildToolsVersion

    defaultConfig {
        applicationId = "com.hoopow.apps.quiz"
        versionCode = Config.versionCode
        versionName = Config.versionName
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += listOf("env")

    productFlavors {
        create("dev") {
            dimension = "env"
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".dev"
        }

        create("qa") {
            dimension = "env"
            versionNameSuffix = "-qa"
            applicationIdSuffix = ".qa"
        }

        create("prod") {
            dimension = "env"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures.dataBinding = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlin.time.ExperimentalTime",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }

    hilt {
        enableExperimentalClasspathAggregation = true
    }

    lint {
        isAbortOnError = false
    }

    // https://github.com/Kotlin/kotlinx.coroutines/issues/2023
    // for JNA and JNA-platform
    packagingOptions {
        resources {
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Base exposes bunch of dependencies via api
    implementation(project(":infra:base"))

    // :app specific Dependencies
    implementation(project(":infra:worker"))

    // Features
    implementation(project(":home"))
    implementation(project(":quiz"))
    implementation(project(":signin"))
    implementation(project(":splash"))

    // Repeated in app and in base-android-library.kts)
    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltCompiler)

    testImplementation(Libs.Testing.junit)
    testImplementation(Libs.Testing.assertJ)
    testImplementation(Libs.Testing.mockitoKotlin)
    testImplementation(Libs.Testing.mockWebServer)

    androidTestImplementation(Libs.Testing.extJunit)
    androidTestImplementation(Libs.Testing.espressoCore)
}