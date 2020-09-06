import java.util.*

fun main() {
    val input = Scanner(System.`in`)   
    val word = input.next()
    val alphabet = ('a'..'z').fold("") { acc, c -> acc + c }
    print(alphabet.contains(word))
}
