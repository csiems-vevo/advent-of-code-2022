fun countFullyOverlappingPairs(input: List<Pair<IntRange,IntRange>>): Int {
    return input.count { (first, second) ->
        first fullyContains second || second fullyContains first
    }
}

fun countPartialOverlappingPairs(input: List<Pair<IntRange,IntRange>>): Int {
    return input.count { (first, second) -> first overlaps second }
}

private infix fun IntRange.fullyContains(other: IntRange): Boolean = this.all { it in other }

private infix fun IntRange.overlaps(other: IntRange): Boolean = this.any { it in other }



