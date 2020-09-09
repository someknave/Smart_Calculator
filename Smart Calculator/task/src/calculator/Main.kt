
package calculator

import java.math.BigInteger
import java.util.*

class Stack(size:Int){
    private var stackSize = size
    private var stackArr = Array(stackSize) {""}
    private var top = -1

    fun push(entry: String) {
        if (this.isStackFull()) {
            this.increaseStackCapacity()
        }
        top += 1
        this.stackArr[top] = entry
    }
    fun pop(): String? {
        if (this.isStackEmpty()) {
            println("Stack empty")
            return null
        }
        val out =stackArr[top]
        top--
        return out
    }
    fun size(): Int {
        return top + 1
    }
    fun peek(): String {
        return stackArr.getOrNull(top).toString()
    }
    private fun increaseStackCapacity() {
        val newStack = Array(this.stackSize * 2) {""}
        for (i in 0 until stackSize) {
            newStack[i] = this.stackArr[i]
        }
        this.stackArr = newStack
        this.stackSize = this.stackSize * 2
        return
    }
    fun isStackEmpty(): Boolean {
        return top == - 1
    }
    private fun isStackFull(): Boolean {
        return top == stackSize - 1
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    var looping = true
    while (looping) {
        val phrase = scanner.nextLine()
        looping = phrase.parse()
    }
}

val variables = mutableMapOf<String, BigInteger>()

fun String?.parse(): Boolean {
    if (this.isNullOrEmpty()) return true
    if (this[0] == '/') return this.command()
    return try {
        this.lineToEquation().solve()
    } catch (e: IllegalArgumentException) {
        println("Invalid Expression")
        true
    }
}

fun String.command(): Boolean {
    return when (this) {
        "/exit" -> { println("Bye!"); false
        }
        "/help" -> { println("The program calculates addition and subtraction. Use + and - signs between digits"); true
        }
        else -> { println("Unknown command"); true
        }
    }
}

fun String.lineToEquation(): List<String> {
    val punct = Regex("\\p{Punct}")
    val spaced = punct.replace(this) { " ${it.value} " }
    val list = spaced.split(Regex("\\s"))
    val output = mutableListOf<String>()
    var negative = false
    var operation = false
    val stack = Stack(6)
    loop@ for (word in list) {
        when {
            word == "" -> continue@loop
            !operation && word in "*/=^)" -> throw IllegalArgumentException("")
            !operation && word == "-" -> negative = !negative
            !operation && word == "+" -> if (stack.peek() != "+") throw IllegalArgumentException("")
            !operation && word == "(" -> {
                if (negative) {output.add("-1"); stack.push("*"); negative=false }
                stack.push("(")
            }
            !operation && !negative -> {output.add(word); operation = true}
            !operation -> {output.add("-$word"); operation = true; negative = false}
            word == "^" -> {
                if (stack.peek() == "^") {output.add(stack.pop()!!)}
                stack.push("^")
                operation = false
            }
            word in "*/" -> {
                while (stack.peek() in "^*/") {output.add(stack.pop()!!)}
                stack.push(word)
                operation = false
            }
            word in "+-" -> {
                if (word == "-") negative = !negative
                while (stack.peek() in "^*/+") {output.add(stack.pop()!!)}
                stack.push("+")
                operation = false
            }
            word == "=" -> {
                while (stack.peek() in "^*/+") {output.add(stack.pop()!!)}
                stack.push("=")
                operation = false
            }
            word == ")" -> {
                while (stack.peek() in "^*/+") {
                    output.add(stack.pop()!!)
                }
                if (stack.peek() != "(") {
                    println("Invalid parenthesis")
                    return emptyList()
                }
                stack.pop()

            }
            else -> throw IllegalArgumentException("")
        }
    }
    while(stack.size() > 0) output.add(stack.pop()!!)
    return output
}
fun String.isVarName(): Boolean {
    return this.isNotEmpty() && this.all {
        it in 'a'..'z'|| it in 'A'..'Z'
    }
}

fun List<String>.solve(): Boolean {
    val stack = Stack(10)
    for (i in this.indices) {
        if (this[i] == "=" && i != this.size-1) {
            println("Invalid assignment")
            return true
        }
        if (this[i] in "=+*/^") {
            val b = stack.pop()?:return true
            val a = stack.pop()?:return true
            val ans = this[i].binaryOperation(a, b) ?: return true
            stack.push(ans.toString())
        } else stack.push(this[i])
    }
    if (stack.isStackEmpty()) return true
    val output = stack.pop()?.toVariableOrInt()
    if (stack.isStackEmpty()) {
        println(output)
    }   else println("Invalid expression")
    return true
}
fun String.toVariableOrInt(): BigInteger? {
    var sign = BigInteger.ONE
    val positive = if (this[0] == '-') {
        sign = -sign
        this.drop(1)
    } else this
    val output = when {
        positive in variables.keys -> variables[positive]?.times(sign)
        positive.isVarName() -> {
            println("Unknown Variable")
            return null
        }
        else -> positive.toBigIntegerOrNull()?.times(sign)
    }
    if (output == null) println("Invalid expression")
    return output
}

fun String.binaryOperation(a: String, b: String): BigInteger? {
    val bInt = b.toVariableOrInt() ?: return null
    if (this == "=") {
        if (a.isVarName()) { variables[a] = bInt
        } else { println("Invalid variable name")}
        return null
    }
    val aInt = a.toVariableOrInt() ?: return null
    return when (this) {
        "+" -> aInt + bInt
        "*" -> aInt * bInt
        "/" -> aInt / bInt
        "^" -> Array<BigInteger>(bInt.toInt()) {aInt}.fold(BigInteger.ONE) { acc, i -> acc * i }
        else -> {println("Invalid expression"); null}
    }
}





