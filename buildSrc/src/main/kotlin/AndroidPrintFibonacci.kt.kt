import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.ByteArrayOutputStream

abstract class AndroidPrintFibonacci : DefaultTask() {
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

        project.exec {
            commandLine(
                "adb", "shell", "am", "startservice",
                "-n", "cn.connor.kotlin/.FibonacciService",
                "--ei", "N", length.toString()
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