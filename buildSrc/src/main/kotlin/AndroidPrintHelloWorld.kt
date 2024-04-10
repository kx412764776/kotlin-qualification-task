import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.ByteArrayOutputStream

abstract class AndroidPrintHelloWorld : DefaultTask() {

    init {
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
            ?: throw IllegalArgumentException("\u001B[31mNo emulator found running. Please start an emulator and try again.\u001B[0m")
    }
    @TaskAction
    fun printHelloWorld() {
        // If MainActivity is running, restart it
        project.exec {
            commandLine(
                "adb", "shell", "am", "force-stop",
                "cn.connor.kotlin"
            )
        }
        project.exec {
            commandLine(
                "adb", "shell", "am", "start",
                "-n", "cn.connor.kotlin/.MainActivity"
            )
            standardOutput = ByteArrayOutputStream()
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