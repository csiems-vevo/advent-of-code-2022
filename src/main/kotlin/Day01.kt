import java.io.File

fun calculateMaxElfCalories(data: List<Int>): Int {
    return data.first()
}

fun calculateTopNElfCalories(n: Int, data: List<Int>): Int {
    return data.take(n).sum()
}

fun File.toElfCalorieList(): List<Int> =
    this.splitByNewline()
        .map { it.lines().sumOf(String::toInt) }
        .sortedDescending()
