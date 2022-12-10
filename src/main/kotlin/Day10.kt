import kotlin.math.floor

fun part01(input: List<String>): Int {

    val values = runProgram(input)

    return listOf(
        values.take(20).sum() * 20,
        values.take(60).sum() * 60,
        values.take(100).sum() * 100,
        values.take(140).sum() * 140,
        values.take(180).sum() * 180,
        values.take(220).sum() * 220
    ).sum()
}

fun part02(input: List<String>): String {

    val values = runProgram(input)

    val printOut = buildString {
        values.forEachIndexed { index, char ->
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
