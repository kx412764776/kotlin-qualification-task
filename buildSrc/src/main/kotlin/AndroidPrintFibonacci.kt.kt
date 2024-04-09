import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class AndroidPrintFibonacci : DefaultTask() {
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
        }
        // Setup a process builder for adb logcat
        val processBuilder = ProcessBuilder("adb", "logcat", "-s", "Fibonacci:*")
        val process = processBuilder.start()

        val bufferedReader = process.inputStream.bufferedReader()

        // Poll the logs and stop when the desired output is found or after timeout
        val startTime = System.currentTimeMillis()
        val timeout = 2000L
        var found = false

        while (System.currentTimeMillis() - startTime < timeout && !found) {
            val line = bufferedReader.readLine() ?: break
            if (line.contains("Fibonacci")) {
                println(line.substringAfter(": ").trim())
                found = true
            }
        }

        // Clean up
        bufferedReader.close()
        process.destroy()

        if (!found) {
            throw RuntimeException("Fibonacci sequence not found in logs within timeout period")
        }
    }
}