fun useCrane9000(input: String): String {
    val stacks = input.extractStackedBoxes()
    val commands = input.extractMoveCommands()

    return stacks.processCrane9000MoveCommands(commands).toTopLine()
}

fun useCrane9001(input: String): String {
    val stacks = input.extractStackedBoxes()
    val commands = input.extractMoveCommands()

    return stacks.processCrane9001MoveCommands(commands).toTopLine()
}

private data class Command(val amount: Int, val from: Int, val to: Int)

private fun List<ArrayDeque<Char>>.processCrane9000MoveCommands(commands: List<Command>): List<ArrayDeque<Char>> {
    commands.forEach { this.crane9000Move(it) }
    return this
}

private fun List<ArrayDeque<Char>>.crane9000Move(command: Command) {
    for (i in 1 .. command.amount) {
        val box = this[command.from - 1].removeFirst()
        this[command.to - 1].addFirst(box)
    }
}

private fun List<ArrayDeque<Char>>.processCrane9001MoveCommands(commands: List<Command>): List<ArrayDeque<Char>> {
    commands.forEach { this.crane9001Move(it) }
    return this
}

private fun List<ArrayDeque<Char>>.crane9001Move(command: Command) {
    val tempDeque = ArrayDeque<Char>()
    for (i in 1 .. command.amount) {
        val box = this[command.from - 1].removeFirst()
        tempDeque.addLast(box)
    }

    for (i in 1..tempDeque.size) {
        val inflight = tempDeque.removeLast()
        this[command.to - 1].addFirst(inflight)
    }

}

private fun List<ArrayDeque<Char>>.toTopLine() = this.map { it.first() }.joinToString("")

private fun String.extractStackedBoxes(): List<ArrayDeque<Char>> {
    val boxRows =  this.substringBefore("\n\n").lines().map { it.chunked(4) }
    val columns = boxRows.maxByOrNull { it.size }?.map { ArrayDeque<Char>(listOf()) } ?: emptyList()

    boxRows.forEach { row ->
        row.forEachIndexed { index, box ->
            box.trim()
                .let { boxString ->
                    if (boxString.isNotBlank() && boxString.all { !it.isDigit() }) {
                        val boxChar = boxString.filter { it.isLetter() }.toCharArray().single()
                        columns[index].addLast(boxChar)
                    }
                }
        }
    }
    return columns
}

private fun String.extractMoveCommands() = this.substringAfter("\n\n").trim().lines().map { it.toCommand() }

private fun String.toCommand(): Command = this.split(" ")
    .filter { string -> string.all { it.isDigit() } }
    .map { it.toInt() }
    .let { (amount, from, to) -> Command(amount, from, to) }




