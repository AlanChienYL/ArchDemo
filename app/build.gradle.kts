plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}
android {
    compileSdk = 31
    defaultConfig {
        applicationId = "alan.practice.archdemo"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }
    defaultConfig {

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        unitTests.all {
            it.jvmArgs("-noverify")
        }
    }
}

dependencies {
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraintlayout)
    implementation(Dependencies.AndroidX.lifecycleViewModelKtx)
    implementation(Dependencies.AndroidX.roomKtx)
    kapt(Dependencies.AndroidX.roomCompiler)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.Kotlin.coroutines)
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)

    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.truth)
    testImplementation(Dependencies.Test.coroutine)
    testImplementation(Dependencies.Test.androidxCore)
    testImplementation(Dependencies.Test.robolectric)
    testImplementation(Dependencies.Test.koinTest)
    testImplementation(Dependencies.Test.koinTestJunit4)
    testImplementation(Dependencies.Test.androidxTestCoreKtx)
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}