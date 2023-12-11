import kotlin.math.pow

fun main() {
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<String>): Int {
    return input.asSequence()
        .map { parseCard(it) }
        .map { (_, winning, other) ->
            other.count { it in winning }
        }
        .map { 2f.pow(it - 1).toInt() }
        .sum()
}

private fun part2(input: List<String>): Int {
    val map = input.asSequence()
        .map { parseCard(it) }
        .fold(mutableMapOf<Int, Int>()) { acc, (id, winning, other) ->
            val thisCount = acc[id]?.let { it + 1 } ?: 1
            acc[id] = thisCount
            val count = other.count { it in winning }
            (id + 1..id + count).map { index ->
                if (acc.containsKey(index)) {
                    acc[index] = acc[index]!! + thisCount
                } else {
                    acc[index] = thisCount
                }
            }
            acc
        }
    return map.values.sum()
}

private data class Card(
    val id: Int,
    val winning: List<Int>,
    val other: List<Int>,
)

private fun parseCard(line: String): Card {
    val (cardPart, numbersPart) = line.split(':')
    val id = cardPart.split(' ').last().toInt()
    val (winningNumbers, otherNumbers) = numbersPart.trim().split('|')
    val winning = winningNumbers.trim()
        .splitToSequence(' ')
        .filter { it.isNotBlank() }
        .map { it.trim().toInt() }
    val other = otherNumbers.trim()
        .splitToSequence(' ')
        .filter { it.isNotBlank() }
        .map { it.trim().toInt() }
    return Card(id, winning.toList(), other.toList())
}
