import java.util.*
import kotlin.random.Random

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
   /* val size = scanner.nextInt()
    val array = IntArray(size) { scanner.nextInt() }
    val rotation = scanner.nextInt()
    val new = array.rightRotation(rotation % size)
    for (e in new) print("$e ")*/
    val a = IntArray(4) { scanner.nextInt() }
    println(a.pOrder().toPassword())



}
fun IntArray.rightRotation(n: Int): List<Int>{
    val front = this.dropLast(n)
    val back = this.drop(this.size - n)
    return back.plus(front)
}
fun List<Int>.toPassword():String {
    val alphabet = ('a'..'z').toList()
    var output = ""
    var type = 0
    var id = 0
    for (e in this) {
        if (e == 3) {
            id = if (type == 3) {
                (Random.nextInt(9) + id) % 10
            } else {Random.nextInt(10)}
            output += id.toString()
            type = 3
        } else {
            id = if (e == type) {
                (Random.nextInt(25) + id) % 26
            } else {Random.nextInt(26)}
            type = e
            if (e == 1) {
                output += alphabet[id]
            } else { output += alphabet[id].toUpperCase() }
        }
    }
    return output
}




fun IntArray.pOrder(): List<Int> {
    val n = this[3]
    val a = this.copyOf()
    a[3] -= (a[0] + a[1] + a[2])
    fun prob(num: Int):Int {
        var total = 0
        for (i in 0..2) {
            total += a[i]
            if (total > num) {
                a[i]--
                return i + 1
            }
        }
        a[3]--
        return Random.nextInt(3) + 1
    }
    val output = mutableListOf<Int>()
    for (i in 0 until n) {
        output.add(i, prob(Random.nextInt(n - i)))
    }
    return output

}