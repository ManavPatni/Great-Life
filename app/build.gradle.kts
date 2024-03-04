plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.thecodeproject.in.greatlife"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thecodeproject.in.greatlife"
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

    buildFeatures {
        viewBinding = true
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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")

    //SDP & SSP-auto sizing
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    runtimeOnly("com.intuit.ssp:ssp-android:1.1.0")

    //google auth
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Image loading library Glider
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //image blur
    implementation("jp.wasabeef:glide-transformations:4.3.0")

    //lottie
    implementation ("com.airbnb.android:lottie:6.3.0")

}