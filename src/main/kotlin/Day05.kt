fun useCrane9000(input: String): String {
    val stacks = input.extractStackedBoxes()
    val commands = input.extractMoveCommands()

    return stacks.processMoveCommands(commands, true).toTopLine()
}

fun useCrane9001(input: String): String {
    val stacks = input.extractStackedBoxes()
    val commands = input.extractMoveCommands()

    return stacks.processMoveCommands(commands, false).toTopLine()
}

private data class Command(val amount: Int, val from: Int, val to: Int)

private fun List<ArrayDeque<Char>>.processMoveCommands(commands: List<Command>, reverse: Boolean): List<ArrayDeque<Char>> {
    commands.forEach { this.move(it, reverse) }
    return this
}

private fun List<ArrayDeque<Char>>.move(command: Command, reverse: Boolean) {
    val boxesToMove = ArrayDeque<Char>()
    for (i in 1 .. command.amount) {
        val box = this[command.from - 1].removeFirst()
        boxesToMove.addLast(box)
    }

    for (i in 1..boxesToMove.size) {
        val inFlight = if (reverse) boxesToMove.removeFirst() else boxesToMove.removeLast()
        this[command.to - 1].addFirst(inFlight)
    }
}

private fun List<ArrayDeque<Char>>.toTopLine() = this.map { it.first() }.joinToString("")

private fun String.extractStackedBoxes(): List<ArrayDeque<Char>> {
    val rows =  this.substringBefore("\n\n").lines()
    return (1..rows.last().length step 4).map { index ->
        rows
            .mapNotNull { it.getOrNull(index) }
            .filter { it.isUpperCase() }
            .let { ArrayDeque(it) }
    }
}

private fun String.extractMoveCommands() = this.substringAfter("\n\n").trim().lines().map { it.toCommand() }

private fun String.toCommand(): Command = this.split(" ")
    .filter { string -> string.all { it.isDigit() } }
    .map { it.toInt() }
    .let { (amount, from, to) -> Command(amount, from, to) }




