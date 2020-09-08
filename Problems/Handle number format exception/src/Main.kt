fun parseCardNumber(cardNumber: String): Long {
    if (cardNumber.length != 19) { throw IllegalArgumentException("Incorrect Length") }
    for (i in listOf(4, 9, 14)) {
        if (cardNumber[i] != ' ') { throw IllegalArgumentException("Incorrect Spacing") }
    }
    val nospace = cardNumber.filterIndexed { i, _ -> i % 5 != 4 }
    return nospace.toLong()
}
