object Libs {
    const val coreKtx = "androidx.core:core-ktx:1.5.0"
    const val appcompat = "androidx.appcompat:appcompat:1.3.0"
    const val material = "com.google.android.material:material:1.3.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"

    const val amplifyFrameworkCoreKotlin = "com.amplifyframework:core-kotlin:0.1.8"
    const val amplifyFrameworkCognito = "com.amplifyframework:aws-auth-cognito:1.17.4"
    const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:1.1.5"

    private const val HILT_VERSION = "2.37"
    const val hiltAndroid = "com.google.dagger:hilt-android:$HILT_VERSION"
    const val hiltAndroidPlugin = "com.google.dagger:hilt-android-gradle-plugin:$HILT_VERSION"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$HILT_VERSION"

    private const val LIFECYCLE_VERSION = "2.3.1"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:$LIFECYCLE_VERSION"
    const val activityKtx = "androidx.activity:activity-ktx:1.2.3"

    private const val NAVIGATION_VERSION = "2.3.5"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"

    const val lottie = "com.airbnb.android:lottie:3.7.0"

    private const val RETROFIT_VERSION = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val converterGson = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
    const val gson = "com.google.code.gson:gson:2.8.7"

    private const val OKHTTP_VERSION = "4.9.1"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"

    object Testing {
        const val junit = "junit:junit:4.13.2"
        const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:3.2.0"
        const val assertJ = "org.assertj:assertj-core:3.18.0"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$OKHTTP_VERSION"
        const val turbine = "app.cash.turbine:turbine:0.5.2"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0"

        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}