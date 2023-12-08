fun main() {
    val input = readInput("Day03")
    part1(input).println()
}

private fun part1(input: List<String>): Int {
    if (input.isEmpty()) return 0
    return input.mapIndexed { index, s ->
        s.numberRangesSequence()
            .filter {
                val wider = it.wider(s.length - 1)
                s.hasSymbolsInRange(wider) ||
                    input.getOrNull(index - 1)?.hasSymbolsInRange(wider) == true ||
                    input.getOrNull(index + 1)?.hasSymbolsInRange(wider) == true
            }
            .sumOf { s.slice(it).toInt() }
    }.sum()
}

private fun String.hasSymbolsInRange(r: IntRange): Boolean {
    return subSequence(r).any { it != '.' && !it.isDigit() }
}

private fun IntRange.wider(maxEnd: Int): IntRange =
    (kotlin.math.max(0, start - 1))..(kotlin.math.min(maxEnd, endInclusive + 1))

private fun String.numberRangesSequence(): Sequence<IntRange> {
    return sequence {
        var index = 0
        while (index < length) {
            if (get(index).isDigit()) {
                var lastIndex = index + 1
                while (lastIndex < length && get(lastIndex).isDigit()) lastIndex++
                yield(index until lastIndex)
                index = lastIndex + 1
            } else {
                index++
            }
        }
    }
}