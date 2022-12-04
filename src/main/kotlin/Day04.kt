fun countFullyOverlappingPairs(input: List<Pair<IntRange,IntRange>>): Int {
    return input.count { (first, second) ->
        first.fullyContains(second) || second.fullyContains(first)
    }
}

fun countPartialOverlappingPairs(input: List<Pair<IntRange,IntRange>>): Int {
    return input.count { (first, second) -> first.overlaps(second) }
}

fun IntRange.fullyContains(other: IntRange): Boolean {
    return this.all { it in other }
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return this.any { it in other }
}


