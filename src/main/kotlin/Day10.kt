import kotlin.math.floor

fun part01(input: List<String>): Long {

    val values = runProgram(input)

    return listOf(
        values.score(20),
        values.score(60),
        values.score(100),
        values.score(140),
        values.score(180),
        values.score(220)
    ).sum()
}

fun part02(input: List<String>): String {

    val values = runProgram(input)

    val printOut = buildString {
        values.forEachIndexed { index, _ ->
            val cycle = calculateCycle(index)
            val spriteEdge = values.take(index + 1).sum()
            val spriteCoverage = listOf(spriteEdge, spriteEdge + 1, spriteEdge + 2)

            if(spriteCoverage.contains(cycle)) {
                append("#")
            } else {
                append(".")
            }
            // carriage return after every 40
            if(cycle % 40 == 0) {
                append("\n")
            }
        }
    }

    println(printOut)
    return printOut
}

private fun runProgram(input: List<String>): MutableList<Int> {
    val values = MutableList(240) { 0 }
    values[0] = 1
    var index = 0

    // insert values into a large list according to the index of when they trigger
    input.forEach { command ->
        val parts = command.split(" ")
        if (parts.size == 2) {
            values[index + 2] = values[index + 2] + parts[1].toInt()
            index += 2
        } else {
            index++
        }
    }
    return values
}

private fun calculateCycle(index: Int): Int {
    val cycle = floor(index / 40.0).toInt()
    return index - (cycle * 40) + 1
}

private fun List<Int>.score(amount: Int): Long = this.take(amount).sum() * amount.toLong()
