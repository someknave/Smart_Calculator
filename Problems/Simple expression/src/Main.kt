import java.math.BigInteger
import java.util.*

fun main() {
    val input = Scanner(System.`in`)
    val array = Array(4) { BigInteger(input.next()) }
    val output = -array[0] * array[1] + array[2] - array[3]
    println(output)
}