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
        debugImplementation(libs.compose.ui.tooling)
    }
}

tasks.register<JavaExec>("jvmPrintHelloWorld") {
    group = "jvm"
    description = "Print 'Hello world!' to the console on JVM"
    mainClass.set("cn.connor.JvmPrintHelloWorldKt")
    val compilation = kotlin.jvm().compilations["main"]
    classpath = compilation.compileDependencyFiles + compilation.runtimeDependencyFiles + compilation.output.allOutputs
}

tasks.register<AndroidPrintHelloWorld>("androidPrintHelloWorld") {
    group = "android"
    description = "Print 'Hello world!' to the console on Android"
}

tasks.register<JavaExec>("jvmPrintFibonacciSequence") {
    group = "jvm"
    description = "Print the Fibonacci sequence to the console on JVM"
    mainClass.set("cn.connor.JvmPrintFibonacciKt")
    val compilation = kotlin.jvm().compilations["main"]
    classpath = compilation.compileDependencyFiles + compilation.runtimeDependencyFiles + compilation.output.allOutputs

    val n = project.findProperty("N")?.toString() ?: "-1"
    args(n)
}

tasks.register<AndroidPrintFibonacci>("androidPrintFibonacciSequence") {
    group = "android"
    description = "Print the Fibonacci sequence to the console on Android"
}