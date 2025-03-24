plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.imthiyas.demoproject"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.imthiyas.demoproject"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    viewBinding {
        enable = true
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
//    implementation("com.github.ImthiyaszAlam:AndroidLibrary:v1.0.0")

//    implementation(project(":FlexibleNavigationView"))
    //implementation(project(":MathLibrary"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    project(":FlexibleNavigationView")
    //project(":MathLibrary")
}