import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

// Load credentials
fun loadCredentials(): Properties {
    val credentialFile = rootProject.file("credentials.properties")
    val credentialProperty = Properties()
    credentialProperty.load(FileInputStream(credentialFile))
    return credentialProperty
}

val credentials = loadCredentials()
android {
    namespace = "com.starzplay.starzlibrary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        // Use the properties loaded above
        buildConfigField("String", "BASE_URL", credentials["BASE_URL"].toString())
        buildConfigField("String", "ACCESS_TOKEN", credentials["ACCESS_TOKEN"].toString())

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.okhttp)

    // OkHttp
    implementation(libs.okhttp3.okhttp.v4100)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}