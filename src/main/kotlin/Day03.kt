fun calculateMisplacedItemPrioritySum(input: List<String>): Int {
    return input.fold(0) { acc, rucksack ->
        acc + rucksack.chunked(rucksack.length / 2)
            .commonElement()
            .toScore()
    }
}

fun calculateBadgeItemPrioritySum(input: List<String>): Int {
    return input.chunked(3)
        .fold(0) { acc, elfGroup ->
            acc + elfGroup.commonElement().toScore()
        }
}

private fun List<String>.commonElement(): Char {
    return this.map { it.toSet() }
        .reduce { acc, next -> acc intersect next }
        .single()
}

private fun Char.toScore(): Int {
    val offset = if (this.isLowerCase()) 96 else 38 // find offset between ascii and priority values
    return this.code - offset
}

