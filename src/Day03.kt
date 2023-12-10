fun main() {
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
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

private fun part2(input: List<String>): Int {
    if (input.isEmpty()) return 0
    return input.mapIndexed { index, s ->
        s.asteriskIndexSequence()
            .map { it.wider() }
            .map {
                val gears = s.findNumbersAroundRange(it)
                    .plus(input.getOrNull(index - 1)?.findNumbersAroundRange(it).orEmpty())
                    .plus(input.getOrNull(index + 1)?.findNumbersAroundRange(it).orEmpty())
                    .toList()
                if (gears.size > 1) {
                    gears.fold(1) { acc, i -> acc * i }
                } else {
                    0
                }
            }.sum()
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

private fun String.asteriskIndexSequence(): Sequence<Int> {
    return sequence {
        var index = 0
        while (index < length) {
            if (get(index) == '*') {
                yield(index)
            }
            index++
        }
    }
}

private fun Int.wider(): IntRange = (this - 1)..(this + 1)

private fun String.findFullNumberRange(index: Int): IntRange {
    var startIndex = index
    var endIndex = index
    while (startIndex >= 0 && get(startIndex).isDigit()) startIndex--
    if (startIndex < index) startIndex++
    while (endIndex < length && get(endIndex).isDigit()) endIndex++
    if (endIndex > index) endIndex--
    return startIndex..endIndex
}
private fun String.findNumbersAroundRange(r: IntRange): Sequence<Int> {
    return sequence {
        var index = r.first
        while (index < length && index <= r.last) {
            if (get(index).isDigit()) {
                val fullRange = findFullNumberRange(index)
                yield(substring(fullRange).toInt())
                index = fullRange.last + 1
            } else {
                index++
            }
        }
    }
}