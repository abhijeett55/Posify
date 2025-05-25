        plugins {
            alias(libs.plugins.google.services)
    alias(libs.plugins.android.application)


}

android {
    namespace = "com.example.posify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.posify"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cardview)
    implementation(libs.firebase.firestore)
    implementation(libs.google.services)
    implementation(libs.firebase.bom)
    implementation(libs.firebase.analytics)
    implementation(libs.volley)
    implementation(libs.logging.interceptor)
    implementation(libs.recyclerview)
    implementation(libs.gson)
    implementation(libs.glide)
    implementation(libs.okhttp)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor("com.github.bumptech.glide:compiler:5.0.0-rc01")


}