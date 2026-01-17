// Module build.gradle.kts (Module: MathLibrary)
plugins {
    id("com.android.library")
    id("maven-publish")
}
android {
    namespace = "com.imthiyas.mathlibrary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}






group = "com.github.ImthiyaszAlam" // Replace with your GitHub username
version = "v1.0.0"             // Set your desired version here

//afterEvaluate {
//    publishing {
//        publications {
//            create<MavenPublication>("maven") {
//                from(components["release"]) // Publishes the Android library’s release variant
//
//                groupId = "com.github.ImthiyaszAlam" // Replace with your GitHub username
//                artifactId = "MathLibrary"           // Replace with your library name
//                version = "1.0.0"                    // Define your version
//            }
//        }
//    }
//}
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.github.ImthiyaszAlam" // Replace with your GitHub username
                artifactId = "AndroidLibrary"           // Replace with your library name
                version = "v1.0.0"                    // Define your version

                // Publish the AAR file for Android libraries
                artifact("$buildDir/outputs/aar/MathLibrary-release.aar")
            }
        }
    }
}







