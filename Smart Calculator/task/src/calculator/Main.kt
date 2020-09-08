
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

val variables = mutableMapOf<String, Int>()

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

fun String.lineToEquation(): List<Pair<String, Char>> {
    val punct = Regex("\\p{Punct}")
    val spaced = punct.replace(this) { " ${it.value} " }
    val list = spaced.split(Regex("\\s"))
    val output = mutableListOf<Pair<String, Char>>()
    var negative = false
    var operation = '#'
    var num = ""
    loop@ for (word in list) {
        when (word) {
            "" -> continue@loop
            "+" -> if (num != "") operation = '+'
            "-" -> {
                negative = !negative
                if (operation == '#' && num != "") operation = '+'
            }
            "=" -> if (num != "") {operation = '='} else throw IllegalArgumentException("")
            else -> {
                if (num != "") {
                    if (operation == '#') throw IllegalArgumentException("")
                    output.add(num to operation)
                }
                num = if (negative) "-$word" else word
                negative = false
                operation = '#'
            }
        }
    }
    if (operation != '#') throw IllegalArgumentException("")
    output.add(num to ' ')
    return output
}
fun String.isVarName(): Boolean {
    return this.isNotEmpty() && this.all {
        it in 'a'..'z'|| it in 'A'..'Z'
    }
}

fun List<Pair<String, Char>>.solve(): Boolean {
    var assign = ""
    var ans = 0
    var operator = '+'
    loop@ for (i in this.indices) {
        if (this[i].second == '=') {
            when {
                i > 0 -> { println("Invalid assignment"); return true}
                this[i].first.isVarName() -> { assign = this[i].first; continue@loop }
                else -> { println("Invalid identifier"); return true }
            }
        }
        var new = this[i].first
        var sign = 1
        if (new[0] == '-') { sign = -1; new = new.drop(1) }
        when {
            new in variables.keys && operator == '+' -> { ans += sign * variables[new]!!}
            new.isVarName() -> { println("Unknown variable"); return true }
            new.any {it in 'a'..'z'|| it in 'A'..'Z'} -> { println("Invalid variable"); return true }
            else -> ans +=  new.toIntOrNull()?.times(sign)?: throw IllegalArgumentException("")
        }
        operator = this[i].second
    }
    if (assign == "") {println(ans); return true}
    variables[assign] = ans
    return true
}





