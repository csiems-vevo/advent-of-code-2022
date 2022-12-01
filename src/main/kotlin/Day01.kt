fun calculateMaxElfCalories(data: List<List<Int>>): Int {
    return data.maxOfOrNull { it.sum() } ?: 0
}

fun calculateTopNElfCalories(topN: Int, data: List<List<Int>>): Int {
    return data.sortedByDescending { it.sum() }.take(topN).flatten().sum()
}
