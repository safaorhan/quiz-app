dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "BDouin Quiz"
include(":api")
include(":app")
include(":auth")
include(":home")
include(":infra:base")
include(":infra:worker")
include(":quiz")
include(":signin")
include(":splash")
include(":ui-components")
include(":infra:test-support")
