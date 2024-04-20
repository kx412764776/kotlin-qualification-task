import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream

abstract class AndroidPrintHelloWorld : DefaultTask() {

    init {
        this.dependsOn("assembleDebug")
    }

    @TaskAction
    fun printHelloWorld() {

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
                Thread.sleep(2000)
            }
        } else {
            project.exec {
                commandLine(
                    "adb", "-s", emulatorId, "shell", "am", "force-stop", packageName
                )
            }
            project.exec {
                commandLine(
                    "adb", "-s", emulatorId, "shell", "am", "start",
                    "-n", "$packageName/.MainActivity"
                )
                standardOutput = ByteArrayOutputStream()
            }
        }

        // Poll the logs and stop when the desired output is found or after timeout
        val startTime = System.currentTimeMillis()
        val timeout = 2000L
        var found = false

        while (System.currentTimeMillis() - startTime < timeout && !found) {
            val log = ProcessBuilder("adb", "logcat", "-d")
                .start()
                .inputStream
                .bufferedReader()
                .useLines { lines ->
                    lines.filter { it.contains("HelloWorld") }
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