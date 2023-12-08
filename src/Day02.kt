fun main() {
    val input = readInput("Day02")
    part1(input).println()
}

private fun part1(input: List<String>): Int {
    return parseGames(input)
        .filter { game -> game.sets.all { it.meetsConstraint(gameConstraint) } }
        .sumOf { it.id }
}

private val gameConstraint = GameSet(12, 13, 14)

private fun GameSet.meetsConstraint(constraint: GameSet): Boolean {
    return reds <= constraint.reds && greens <= constraint.greens && blues <= constraint.blues
}

private fun parseGames(input: List<String>): List<Game> {
    return input.map { line ->
        val colonIndex = line.indexOf(':')
        val gameId = line.subSequence(gamePrefix.length, colonIndex).toString().toInt()
        val sets = line.subSequence(colonIndex + 1, line.length)
            .splitAsCharSequence(';')
            .map { parseSet(it.trim()) }
            .toList()
        Game(gameId, sets)
    }
}

private fun parseSet(input: CharSequence): GameSet {
    var reds = 0
    var greens = 0
    var blues = 0
    input.splitAsCharSequence(',')
        .forEach { block ->
            val (count, color) = block.trim().split(' ')
            when (color) {
                "red" -> reds = count.toInt()
                "green" -> greens = count.toInt()
                "blue" -> blues = count.toInt()
                else -> throw Exception("Unsupported color: $color")
            }
        }
    return GameSet(reds, greens, blues)
}

private data class Game(
    val id: Int,
    val sets: List<GameSet>,
)

private data class GameSet(
    val reds: Int,
    val greens: Int,
    val blues: Int,
)
    
const val gamePrefix = "Game "
