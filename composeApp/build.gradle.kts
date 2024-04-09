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
        debugImplementation(libs.compose.ui.tooling)
    }
}

tasks.register("jvmPrintHelloWorld") {
    group = "jvm"
    description = "Print 'Hello, world!' to the console on JVM"
    doLast {
        println("Hello, world!")
    }
}

tasks.register("androidPrintHelloWorld") {
    group = "android"
    description = "Print 'Hello world!' to the console on Android"
    doLast {
        // Detect if emulator is running
        ProcessBuilder("adb", "devices")
            .start()
            .inputStream
            .bufferedReader()
            .useLines { lines ->
                lines.filter { it.contains("emulator") }
                    .map { it.substringBefore("\t") }
                    .firstOrNull()
            }
            ?:
            throw IllegalArgumentException("\u001B[31mNo emulator found running. Please start an emulator and try again.\u001B[0m")


        // If MainActivity is running, restart it
        exec {
            commandLine(
                "adb", "shell", "am", "force-stop",
                "cn.connor.kotlin"
            )
        }
        Thread.sleep(1000)
        exec {
            commandLine(
                "adb", "shell", "am", "start",
                "-n", "cn.connor.kotlin/.MainActivity"
            )
        }
        Thread.sleep(3000)
        ProcessBuilder("adb", "logcat", "-d")
            .start()
            .inputStream
            .bufferedReader()
            .useLines { lines ->
                lines.filter { it.contains("HelloWorld") }
                    .map { it.substringAfter(": ").trim() }
                    .lastOrNull() { it.contains("Hello World!") }
                    ?.let { println(it) }
            }
    }
}

tasks.register<JvmPrintFibonacci>("jvmPrintFibonacciSequence") {
    group = "jvm"
    description = "Print the Fibonacci sequence to the console on JVM"
}

tasks.register<AndroidPrintFibonacci>("androidPrintFibonacciSequence") {
    group = "android"
    description = "Print the Fibonacci sequence to the console on Android"
}