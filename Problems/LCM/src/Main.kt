import java.math.BigInteger
import java.util.*

fun main() {
    val input = Scanner(System.`in`)
    val a = BigInteger(input.next())
    val b = BigInteger(input.next())
    val gcd = gcd(a, b)
    print(a/gcd *b)


}

fun gcd(a: BigInteger, b: BigInteger): BigInteger {
    when {
        a == 0.toBigInteger() -> return b
        b == 0.toBigInteger() -> return a
        a % BigInteger.TWO == BigInteger.ZERO -> {
            if(b % BigInteger.TWO == BigInteger.ZERO) {
                return BigInteger.TWO * gcd(a/BigInteger.TWO, b/BigInteger.TWO)
            } else return gcd(a/BigInteger.TWO, b)
        }
        b % BigInteger.TWO == BigInteger.ZERO -> {
            return gcd(a, b/BigInteger.TWO)
        }
        else -> return gcd((a-b).abs(), minOf(a, b))
    }
}