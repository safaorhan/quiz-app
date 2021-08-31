plugins {
    id("base-android-library")
}

dependencies {
    // The dependencies below are exposed to all modules that depend on :infra:base

    // Core
    api(Libs.coreKtx)
    api(Libs.appcompat)
    api(Libs.material)

    // Lifecycle
    api(Libs.viewModelKtx)
    api(Libs.lifecycleCommon)
    api(Libs.activityKtx)

    // Navigation
    api(Libs.navigationFragment)
    api(Libs.navigationUi)
}