import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val size = scanner.nextInt()
    val array = IntArray(size)
    for (i in 0 until size) { array[i] = scanner.nextInt() }
    val check = scanner.nextInt()
    println(array.count() { it == check })
}
