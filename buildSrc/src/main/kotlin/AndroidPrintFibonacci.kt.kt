import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class AndroidPrintFibonacci : DefaultTask() {
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