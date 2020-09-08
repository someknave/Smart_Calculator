import java.util.*

fun main() {
    val words = mutableMapOf<String ,Int>()
    val input = Scanner(System.`in`)
    while(true) {
        val word = input.next()
        if (word == "stop") break
        words[word] = words[word]?.plus(1)?:1
    }
    if (words.isEmpty()) { print(null) } else {
        val max = words.values.max()
        val most = words.filterValues { it == max }
        print(most.keys.first())
    }

}