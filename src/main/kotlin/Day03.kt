fun calculateMisplacedItemPrioritySum(input: List<String>): Int {
    return input.fold(0) { acc, next ->
        val compartments = splitIntoNEqualComponents(next, 2)
        val misplacedItem = compartments.flatMap { c -> c.toSet() }
            .groupingBy { it }
            .eachCount()
            .filter { it.value == 2 }
            .keys
            .first()
         acc + getPriority(misplacedItem)
    }
}

fun calculateBadgeItemPrioritySum(input: List<String>): Int {
    return input.windowed(3, 3, false)
        .fold(0) { acc, next ->
            acc + getPriority(findCommonElement(next))
        }
}

private fun findCommonElement(group: List<String>): Char {
    return group.map { it.toSet() }
        .reduce { acc, next -> acc.intersect(next) }
        .first()
}

private fun splitIntoNEqualComponents(data: String, n: Int): List<String> {
    assert(data.length > n) { "Input length shorter than $n" }
    assert(data.length % n == 0) { "Input length not evenly divisible by $n" }
    return data.chunked(data.length / n)
}

private fun getPriority(c: Char): Int {
    val offset = if (c.isLowerCase()) 96 else 38 // find offset between ascii and priority values
    return c.code - offset
}

