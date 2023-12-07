fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.firstOrNull { it.isDigit() }?.digitToIntOrNull() ?: 0
            val lastDigit = line.lastOrNull { it.isDigit() }?.digitToIntOrNull() ?: 0
            firstDigit * 10 + lastDigit
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
