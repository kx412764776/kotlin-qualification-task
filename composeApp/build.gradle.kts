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
        val emulator = ProcessBuilder("adb", "devices")
            .start()
            .inputStream
            .bufferedReader()
            .useLines { lines ->
                lines.filter { it.contains("emulator") }
                    .map { it.substringBefore("\t") }
                    .firstOrNull()
            }
        if (emulator == null) {
            println("\u001B[31mNo emulator found running. Please start an emulator and try again.\u001B[0m")
            return@doLast
        }

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
                    .firstOrNull { it.contains("Hello World!") }
                    ?.let { println(it) }
            }
    }
}

tasks.register<JvmPrintFibonacci>("jvmPrintFibonacciSequence") {
    group = "jvm"
    description = "Print the Fibonacci sequence to the console on JVM"
}

abstract class JvmPrintFibonacci : DefaultTask() {
    private var _length: Int = 0

    @Option(option = "N", description = "The length of the Fibonacci sequence")
    fun setLength(length: String) {
        _length = length.toIntOrNull() ?: 0
    }

    @get:Input
    val length: Int
        get() = _length

    @TaskAction
    fun printFibonacci() {
        if (length < 0) {
            throw IllegalArgumentException("\u001B[31mPlease provide a positive integer for the length of the Fibonacci sequence.\u001B[0m")

        }
        val sequence = fibonacci(length)
        println(sequence.joinToString(" "))
    }

    private fun fibonacci(n: Int): List<Int> {
        if (n == 0) return emptyList()
        if (n == 1) return listOf(0)

        val result = mutableListOf(0, 1)

        for (i in 2 until n) {
            result.add(result[i - 1] + result[i - 2])
        }
        return result
    }
}

tasks.register<AndroidPrintFibonacci>("androidPrintFibonacciSequence") {
    group = "android"
    description = "Print the Fibonacci sequence to the console on Android"
}

abstract class AndroidPrintFibonacci : DefaultTask() {
    private var _n: Int = 0

    @Option(option = "N", description = "The length of the Fibonacci sequence")
    fun setLength(n: String) {
        _n = n.toIntOrNull()
            ?: throw IllegalArgumentException(
                "\u001B[31mPlease provide a positive integer for the length of the Fibonacci sequence.\u001B[0m"
            )
    }

    @get:Input
    val n: Int
        get() = _n

    @TaskAction
    fun printFibonacci() {
        exec {
            commandLine(
                "adb", "shell", "am", "startservice",
                "-n", "cn.connor.kotlin/.FibonacciService",
                "--ei", "N", n.toString()
            )
        }
        Thread.sleep(3000)
        ProcessBuilder("adb", "logcat", "-d")
            .start()
            .inputStream
            .bufferedReader()
            .useLines { lines ->
                lines.filter { it.contains("Fibonacci") }
                    .map { it.substringAfter(": ").trim() }
                    .firstOrNull()
                    ?.let { println(it) }
            }
    }
}