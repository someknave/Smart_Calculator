import java.util.Scanner

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val ships = Array(3) { input.nextInt() to input.nextInt() }.unzip()
    val emptyRows = listOf(1, 2, 3, 4, 5).minus(ships.first)
    val emptyCols = listOf(1, 2, 3, 4, 5).minus(ships.second)
    emptyRows.listPrint()
    emptyCols.listPrint()

}
fun List<Any>.listPrint() {
    for (i in 0 until this.size - 1) print("${this[i]} ")
    println(this.last())
}
