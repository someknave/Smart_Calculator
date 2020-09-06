import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val size = scanner.nextInt()
    val array = IntArray(size) { scanner.nextInt() }
    val pair = scanner.nextInt() to scanner.nextInt()
    val pairFlipped = pair.second to pair.first
    val pairs = array.toList().zipWithNext()
    when {
        pair in pairs -> println("YES")
        pairFlipped in pairs -> println("YES")
        else -> println("NO")
    }
}
