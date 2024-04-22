import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.ByteArrayOutputStream

abstract class AndroidPrintFibonacci : DefaultTask() {

    init {
        this.dependsOn("assembleDebug")
    }

    private var _length: Int = 0

    @Option(option = "N", description = "The length of the Fibonacci sequence")
    fun setLength(n: String) {
        _length = n.toIntOrNull()
            ?: throw IllegalArgumentException(
                "\u001B[31mPlease provide a positive integer for the length of the Fibonacci sequence.\u001B[0m"
            )
    }

    @get:Input
    val length: Int
        get() = _length

    @TaskAction
    fun getFibonacciFromLog() {

        val emulatorId = AndroidUtils.getEmulatorId()
        val packageName = "cn.connor.kotlin"

        if (!AndroidUtils.isAppInstalled(emulatorId, packageName)) {
            val apkPath = "${project.projectDir}/build/outputs/apk/debug/composeApp-debug.apk"
            AndroidUtils.installApp(project, emulatorId, apkPath)
            Thread.sleep(2000)
        }

        if (!AndroidUtils.checkIfMainActivityIsRunning(emulatorId, packageName)) {
            project.exec {
                commandLine(
                    "adb", "-s", emulatorId, "shell", "am", "start",
                    "-n", "$packageName/.MainActivity"
                )
                standardOutput = ByteArrayOutputStream()
            }
            Thread.sleep(2000)
        }

        project.exec {
            commandLine(
                "adb", "shell", "am", "startservice",
                "-n", "$packageName/.FibonacciService",
                "--ei", "N", length.toString()
            )
            standardOutput = ByteArrayOutputStream()
        }

        // Poll the logs and stop when the desired output is found or after timeout
        val startTime = System.currentTimeMillis()
        val timeout = 10000L
        var found = false

        while (System.currentTimeMillis() - startTime < timeout && !found) {
            val log = ProcessBuilder("adb", "logcat", "-d")
                .start()
                .inputStream
                .bufferedReader()
                .useLines { lines ->
                    lines.filter { it.contains("Fibonacci") }
                        .lastOrNull()
                        ?.substringAfter(": ")
                        ?.trim()
                }
            if (log != null) {
                println(log)
                found = true
            } else {
                Thread.sleep(2000)
            }
        }
    }
}