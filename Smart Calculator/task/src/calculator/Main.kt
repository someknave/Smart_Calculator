package calculator

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    loop@ while (true) {
        val word = scanner.nextLine()
        when (word) {
            "/exit" -> { println("Bye!"); break@loop }
            "/help" -> { println("The program calculates the sum of numbers"); continue@loop }
            "" -> continue@loop
        }
        try {
            println(word.lineToIntList().listAdd())
        } catch (e: NumberFormatException) {
            println("Invalid entry! type '/exit' to exit")

        }
    }

}

fun String.lineToIntList(): List<Int> {
    return this.split(" ").map { it.toInt() }
}
fun List<Int>.listAdd(): Int {
    return this.fold(0) { acc, it -> acc + it }
}

