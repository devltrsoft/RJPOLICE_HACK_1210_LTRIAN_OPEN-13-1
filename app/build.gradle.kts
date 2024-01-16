plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ltrsoft.rajashtanuserapplication"
    compileSdk = 34
    //packagingOptions {
//        exclude("META-INF/DEPENDENCIES")
//        exclude("META-INF/LICENSE")
//        exclude("META-INF/LICENSE.txt")
//        exclude("META-INF/license.txt")
//        exclude("META-INF/NOTICE")
//        exclude("META-INF/NOTICE.txt")
//        exclude("META-INF/notice.txt")
//        exclude("META-INF/ASL2.0")
//        exclude("META-INF/*.kotlin_module")
//    }
    defaultConfig {
        applicationId = "com.ltrsoft.rajashtanuserapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        packagingOptions {
            exclude("META-INF/*")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-messaging:23.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.android.volley:volley:1.2.1")

    implementation ("com.google.android.libraries.places:places:3.3.0")
    implementation ("com.google.maps.android:android-maps-utils:2.2.0")
    implementation ("com.github.jd-alexander:library:1.1.0")

    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.android.volley:volley:1.2.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.airbnb.android:lottie:4.2.2")

    implementation ("com.google.maps:google-maps-services:0.17.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")

    implementation ("com.google.maps.android:android-maps-utils:2.2.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.itextpdf:itextg:5.5.10")


    implementation ("com.google.cloud:google-cloud-translate:2.11.0")



}