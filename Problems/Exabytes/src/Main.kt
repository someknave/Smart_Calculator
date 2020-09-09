import java.math.BigInteger
import java.util.*

fun main() {
    val input = Scanner(System.`in`)
    val a = input.nextInt()
    println(a.toBigInteger().shl(63))
}