import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun CharSequence.splitAsCharSequence(delimiter: Char): Sequence<CharSequence> {
    return sequence {
        var start = 0
        var indexOfDelimiter: Int = -1
        while (indexOfDelimiter != length) {
            indexOfDelimiter = indexOf(delimiter, startIndex = start)
            if (indexOfDelimiter == -1) {
                indexOfDelimiter = length
            }
            yield(start until indexOfDelimiter)
            start = indexOfDelimiter + 1
        }
    }.map { subSequence(it) }
}