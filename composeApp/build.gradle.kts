plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    sourceSets {
        jvm()
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
    }
}

android {
    namespace = "cn.connor.kotlin"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "cn.connor.kotlin"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.material)
        implementation(libs.androidx.constraintlayout)
        testImplementation(libs.junit)
        androidTestImplementation(libs.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        debugImplementation(libs.compose.ui.tooling)
    }
}

tasks.register("printHelloWorldJvm") {
    group = "jvm"
    description = "Prints 'Hello, world!' to the console on JVM"
    doLast {
        println("Hello, world!")
    }
}

tasks.register("printHelloWorldAndroid") {
    group = "android"
    description = "Prints 'Hello, world!' to the console on Android"
    doLast {
//        exec {
//            commandLine(
//                "adb", "shell", "am", "start",
//                "-n", "com.example/.MainActivity",
//                "-a", "android.intent.action.MAIN",
//                "-c", "android.intent.category.LAUNCHER"
//            )
//        }
        exec {
            commandLine(
                "adb", "shell", "echo", "Hello World!"
            )
        }
    }
}