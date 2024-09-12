import org.jetbrains.kotlin.gradle.plugin.extraProperties
import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.sample.zap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sample.lms"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.kotlinCoroutinesAndroid)
    implementation(libs.kotlinCoroutinesCore)
    implementation(libs.retrofit)
    implementation(libs.retrofitMoshiConverter)
    implementation(libs.retrofitCoroutinesAdapter)
    implementation(libs.okhttpLoggingInterceptor)
    implementation(libs.okhttp)

    implementation(libs.koinCore)
    implementation(libs.koinAndroid)

    implementation(libs.moshi)
    implementation(libs.moshiKotlin)
    implementation(libs.glide)
    implementation(libs.splash)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.idle)
}