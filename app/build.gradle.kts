plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.zalmanach"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zalmanach"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val nav_version = "2.7.6"
    val retrofitVersion = "2.9.0"
    val roomVersion = "2.6.0"

    // Fragmente und  Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Feature module Support, für die Unterstützung von Feature-Modulen
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Retrofit zum Abrufen von Daten aus einem API Endpunkt | Moshi zur Konvertierung von JSON-Daten
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    // Coil, um Bilder aus dem Internet zu Laden und anzuzeigen
    implementation("io.coil-kt:coil:2.5.0")

    // Room, zur Loaklen Datenverarbeitung
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Interceptor, für die Protokollierung von Netzwerkanfragen und -antworten/zu Debugging-Zwecken
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ViewModel und LiveData-Komponenten zur Trennung von UI-Logik und Daten
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // Coroutines zur asynchronen Verarbeitung von Daten
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // RecyclerView-Bibliothek
    implementation ("com.google.android.material:material:1.11.0")

    // Material Cardview
    implementation ("com.google.android.material:material:1.11.0")

    // Macht eckige Bilder mittels XML-Code rund
    implementation ("de.hdodenhof:circleimageview:3.1.0")
}