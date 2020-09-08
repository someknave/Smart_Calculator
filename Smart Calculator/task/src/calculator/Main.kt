
package calculator

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    var looping = true
    while (looping) {
        val phrase = scanner.nextLine()
        looping = phrase.parse()
    }
}

fun String?.parse(): Boolean {
    if (this.isNullOrEmpty()) return true
    if (this[0] == '/') return this.command()
    return try {
        this.lineToEquation().solve()
    } catch (e: IllegalArgumentException) {
        println("Invalid expresion")
        true
    }
}

fun String.command(): Boolean {
    when (this) {
        "/exit" -> { println("Bye!"); return false }
        "/help" -> { println("The program calculates addition and subtraction. Use + and - signs between digits"); return true }
        else -> { println("Unknown command"); return true }
    }
}

fun String.lineToEquation(): List<Pair<Char, Int>> {
    val punct = Regex("\\p{Punct}")
    val spaced = punct.replace(this) { " ${it.value} " }
    val list = spaced.split(Regex("\\s"))
    val output = mutableListOf<Pair<Char, Int>>()
    var sign = 1
    var operation = ' '
    loop@ for (word in list) {
        when (word) {
            "" -> continue@loop
            "+" -> operation = '+'
            "-" -> { sign *= -1; operation = '+' }
            else -> {
                if (operation == '#') throw IllegalArgumentException("")
                val num = word.toIntOrNull() ?: throw IllegalArgumentException("")
                output.add(operation to num * sign)
                sign = 1
                operation = '#'
            }
        }
    }
    if (operation != '#') throw IllegalArgumentException("")
    return output
}

fun List<Pair<Char, Int>>.solve(): Boolean {
    var ans = this[0].second
    for (i in 1 until this.size) {
        if (this[i].first == '+') {
            ans += this[i].second
        } else throw IllegalArgumentException("")
    }
    println(ans)
    return true
}


