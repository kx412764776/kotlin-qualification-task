import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class JvmPrintFibonacci : DefaultTask() {
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