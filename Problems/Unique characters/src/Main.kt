import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val word = scanner.next()
    var singles = 0
    for (letter in word) {
        if (word.count { it == letter } == 1) singles++
    }
    print(singles)

}