fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.firstOrNull { it.isDigit() }?.digitToIntOrNull() ?: 0
            val lastDigit = line.lastOrNull { it.isDigit() }?.digitToIntOrNull() ?: 0
            firstDigit * 10 + lastDigit
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            lineCalcValue(it)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private val text2numberMap = arrayOf(
    "zero" to 0,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

private val maxDigitTextLength = text2numberMap.maxOf { it.first.length }

private fun lineCalcValue(line: String): Int {
    var leftIndex = 0
    var rightIndex = line.length - 1
    var firstDigit: Int? = null
    var lastDigit: Int? = null
    val indexRange = line.indices
    while (true) {
        if (leftIndex !in indexRange || rightIndex !in indexRange) break
        if (firstDigit != null && lastDigit != null) break
        if (firstDigit == null) {
            val lc = line[leftIndex]
            if (lc.isDigit()) {
                firstDigit = lc.digitToInt()
            } else {
                line.maxDigitTextSubSeq(leftIndex).digitTextOrNull()?.let {
                    firstDigit = it
                }
            }
        }
        leftIndex++
        if (lastDigit == null) {
            val rc = line[rightIndex]
            if (rc.isDigit()) {
                lastDigit = rc.digitToInt()
            } else {
                line.maxDigitTextSubSeqRight(rightIndex).digitTextOrNullRight()?.let {
                    lastDigit = it
                }
            }
        }
        rightIndex--
    }
    return (firstDigit ?: 0) * 10 + (lastDigit ?: 0)
}

private fun CharSequence.digitTextOrNull(): Int? {
    return text2numberMap.firstOrNull {
        this.startsWith(it.first)
    }?.second
}

private fun String.maxDigitTextSubSeq(startIndex: Int): CharSequence {
    val endIndex = kotlin.math.min(length, startIndex + maxDigitTextLength)
    return subSequence(startIndex, endIndex)
}

private fun CharSequence.digitTextOrNullRight(): Int? {
    return text2numberMap.firstOrNull {
        this.endsWith(it.first)
    }?.second
}

private fun String.maxDigitTextSubSeqRight(endIndex: Int): CharSequence {
    val startIndex = kotlin.math.min(
        endIndex,
        kotlin.math.max(0, endIndex - maxDigitTextLength),
    )
    return subSequence(startIndex, endIndex + 1)
}
