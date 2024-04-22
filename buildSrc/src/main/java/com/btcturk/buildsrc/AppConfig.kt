object AppConfig {
    const val appName = "OzanTechCaseApp"
    const val namespace = "com.ozantech.ozantechcaseapp"
    const val namespaceData = "com.ozantech.ozantechcaseapp.core.data"
    const val namespaceDatabase = "com.ozantech.ozantechcaseapp.core.database"
    const val namespaceModel = "com.ozantech.ozantechcaseapp.core.model"
    const val namespaceNetwork = "com.ozantech.ozantechcaseapp.core.network"
    const val namespaceUiComponents = "com.ozantech.ozantechcaseapp.core.uicomponents"

    // base
    const val applicationId = "com.ozantech.ozantechcaseapp"
    const val compileSdk = 34
    const val minSdk = 24
    const val targetSdk = 33
    const val versionCode = 1
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0

    val versionName = "v$versionMajor.$versionMinor.$versionPatch"

    // version
    const val devVersionName = "1.0.120"
    const val prodVersionName = "1.0.50"

    // flavor
    const val stage = "stage"
    const val prod = "prod"
    const val debugAppIdSuffix = ".debug"
    const val stageAppIdSuffix = ".stage"

    // signing config (just for testing release packages with jks file not for submitting to play store)
    const val storePassword = "demo1234"
    const val keyAlias = "key0"
    const val keyPassword = "demo1234"
    const val devAppName = "OzanTech Stage"
    const val prodAppName = "OzanTech"
    const val demoJksFilePath = "../config/demoJks"
    const val defaultFlavorDimension = "environment"

    const val jvmTarget = "11"
}
