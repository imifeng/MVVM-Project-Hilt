plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "30.0.2"

    signingConfigs {
        create("release") {
            storeFile = File(Signing.StoreFile)
            storePassword = Signing.StorePassword
            keyAlias = Signing.KeyAlias
            keyPassword = Signing.KeyPassword

            isV1SigningEnabled = true
            isV2SigningEnabled = true
        }
    }

    defaultConfig {
        applicationId = Versions.applicationId
        minSdkVersion(Versions.minAndroidSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.verCode
        versionName = Versions.verName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isTestCoverageEnabled = project.hasProperty("coverage")
            isDebuggable = true
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-DEV"

            buildConfigField("String", "API_URL", "\"https://api.github.com/\"")
        }

        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")

            buildConfigField("String", "API_URL", "\"https://api.github.com/\"")
        }
    }

    //自定义打包时apk名称
    applicationVariants.all {
        val buildType = buildType.name
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                if (buildType == "release") {
                    outputFileName = "MVVM_v${versionCode}_${flavorName}_${Versions.dateFormat}.apk"
                }
            }
        }
    }

    //多渠道打包
    flavorDimensions("code")
    productFlavors {
        create("google")
        create("baidu")
        create("huawei")
        create("default")
    }
    productFlavors.all {
        manifestPlaceholders["CHANNEL_VALUE"] = name
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Libs.KOTLIN_VERSION}")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    // hilt
    implementation("com.google.dagger:hilt-android:${Libs.HILT_VERSION}")
    kapt("com.google.dagger:hilt-android-compiler:${Libs.HILT_VERSION}")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:${Libs.HILT_ANDROIDX_VERSION}")
    kapt("androidx.hilt:hilt-compiler:${Libs.HILT_ANDROIDX_VERSION}")

    implementation("androidx.fragment:fragment-ktx:${Libs.FRAGMENT_VERSION}")

    // Lifecycle - ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Libs.LIFECYCLE_VERSION}")
    // Lifecycle - LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Libs.LIFECYCLE_VERSION}")
    // Lifecycle - Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Libs.LIFECYCLE_VERSION}")
    // Lifecycle - Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Libs.LIFECYCLE_VERSION}")
    // Lifecycle - Annotation processor
    // kapt "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
    // Lifecycle - alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation("androidx.lifecycle:lifecycle-common-java8:${Libs.LIFECYCLE_VERSION}")

    // Room
    implementation("androidx.room:room-runtime:${Libs.ROOM_VERSION}")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:${Libs.ROOM_VERSION}")
    kapt("androidx.room:room-compiler:${Libs.ROOM_VERSION}")

    // Work Manager
    implementation("androidx.work:work-runtime-ktx:${Libs.WORK_VERSION}")

    // retrofit2
    implementation("com.squareup.retrofit2:retrofit:${Libs.RETROFIT_VERSION}")
    implementation("com.squareup.retrofit2:converter-moshi:${Libs.RETROFIT_VERSION}")
    // moshi
    implementation("com.squareup.moshi:moshi:${Libs.MOSHI_VERSION}")
    implementation("com.squareup.moshi:moshi-kotlin:${Libs.MOSHI_VERSION}")
    implementation("com.squareup.moshi:moshi-adapters:${Libs.MOSHI_VERSION}")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${Libs.MOSHI_VERSION}")

    // 该项目实现了Room类可用于处理特定版本SQLite的Support系列类和接口。
    // 具体地说，该项目的类将Room与适用于Android的SQLCipher连接起来，后者是SQLite的版本，可对其内容进行透明加密。
//    implementation 'com.commonsware.cwac:saferoom.x:1.2.1'

    // StatusBarUtil
    implementation("com.jaeger.statusbarutil:library:${Libs.STATUS_BAR_UTIL_VERSION}")
    // Glide
    implementation("com.github.bumptech.glide:glide:${Libs.GLIDE_VERSION}")
    annotationProcessor("com.github.bumptech.glide:compiler:${Libs.GLIDE_VERSION}")
    implementation("jp.wasabeef:glide-transformations:${Libs.TRANSFORMATIONS_VERSION}")
}