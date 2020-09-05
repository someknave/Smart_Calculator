package calculator

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val sum = scanner.nextLine().lineToIntList().listAdd()

    println(sum)
}



fun String.lineToIntList(): List<Int> {
    return this.split(" ").map { it.toInt() }
}

fun List<Int>.listAdd(): Int {
    return this.fold(0) { acc, it -> acc + it }
}
