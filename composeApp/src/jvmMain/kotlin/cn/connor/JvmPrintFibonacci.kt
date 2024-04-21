package cn.connor

fun main(args: Array<String>) {
    val n = args.firstOrNull()?.toIntOrNull() ?: -1
        val sequence = fibonacciSequence(n).joinToString(" ")
        println(sequence)
}

fun fibonacciSequence(n: Int): List<Int> {
    if (n > 0) {
        return generateSequence(0 to 1) { it.second to it.first + it.second }
            .map { it.first }
            .take(n)
            .toList()
    } else {
        throw IllegalArgumentException("Please provide a positive integer for the length of the Fibonacci sequence.")
    }
}