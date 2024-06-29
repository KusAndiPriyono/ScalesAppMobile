plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevToolsKSP)
    alias(libs.plugins.googleDaggerHiltAndroid)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.bangkit.scalesappmobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bangkit.scalesappmobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/native-image/resource-config.json"
            excludes += "META-INF/native-image/reflect-config.json"
        }
    }

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Splash API
    implementation(libs.androidx.core.splashscreen)

    //Material Icon
    implementation(libs.androidx.material.icons.extended)

    //Google Fonts
    implementation(libs.androidx.ui.text.google.fonts)

    //Coil
    implementation(libs.coil.compose)

    //DataStore
    implementation(libs.androidx.datastore.preferences)

    //Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Compose Navigation
    implementation(libs.androidx.navigation.compose)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //Paging 3
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    //SwipeRefresh
    implementation(libs.accompanist.swiperefresh)

    //Lottie
    implementation(libs.lottie.compose)

    //Timber
    implementation(libs.timber)

    //Chucker
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)

    //Destination
    implementation(libs.compose.destinations.animations)
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)

    //Collapsing Toolbar
    implementation(libs.toolbar.compose)

    //ImageCropper
    implementation(libs.easycrop)

    // Desugar JDK
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // CALENDAR
    implementation(libs.calendar)

    //iTextPdf
    implementation(libs.itext7.core)

//    // Accompanist System UI Controller Library
//    implementation(libs.accompanist.systemuicontroller)

//    // CLOCK
//    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.2.1")
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

ksp {
    arg("compose-destinations.mode", "destinations")
    arg("compose-destinations.moduleName", "auth")
}
ksp {
    arg("compose-destinations.mode", "destinations")
    arg("compose-destinations.moduleName", "appScales")
}