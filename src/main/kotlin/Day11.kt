import shared.product

fun runMonkeyProgram(rounds: Int, input: List<String>, stressModifier:((value: Long) -> Long)? = null): Long {

    val monkeys = input.map { it.lines() }
        .map { group -> Monkey.of(group) }


    repeat(rounds) {
        monkeys.forEach { monkey ->
            if (stressModifier != null) {
                monkey.inspectItems(monkeys) { stressModifier(it)}
            } else {
                val divisor = monkeys.map { it.divisible }.product()
                monkey.inspectItems(monkeys) { it % divisor }
            }
        }
    }

    return monkeys.sortedByDescending { it.itemsInspected }
        .take(2)
        .map { it.itemsInspected }
        .product()
}

data class Monkey(
    val startingItems: MutableList<Item>,
    val worryLevelTransformer: (old: Long) -> Long,
    val divisible: Long,
    val itemThrowDecision: (old: Long) -> Int
) {

    var itemsInspected = 0L

    companion object {
        fun of(inputGroup: List<String>): Monkey {
            val startingItems = inputGroup[1].split(" ",",")
                .filterNot { it.isEmpty() }
                .drop(2)
                .map { Item(it.toLong()) }
                .toMutableList()
            val worryLevelTransformer = extractWorryLevelTransformer(inputGroup[2])

            val divisible = inputGroup[3].split(" ").last().toLong()
            val trueCondition = inputGroup[4].split(" ").last().toInt()
            val falseCondition = inputGroup[5].split(" ").last().toInt()
            val itemThrowDecision = buildThrowDecision(divisible, trueCondition, falseCondition)

            return Monkey(startingItems, worryLevelTransformer, divisible, itemThrowDecision)
        }
    }

    fun inspectItems(monkeys: List<Monkey>, reliefTransformer: (Long) -> Long) {
        startingItems.forEach { item ->
            val newWorryLevel = reliefTransformer(worryLevelTransformer(item.worryLevel))
            val target = itemThrowDecision(newWorryLevel)
            monkeys[target].startingItems.add(Item(newWorryLevel))
        }
        itemsInspected += startingItems.size
        startingItems.clear()
    }
}

data class Item(val worryLevel: Long)

private fun buildThrowDecision(divisible: Long, trueCondition: Int, falseCondition: Int): (Long) -> Int {

    return fun(old: Long): Int {
        return if ((old % divisible) == 0L) {
            trueCondition
        } else {
            falseCondition
        }
    }
}

private fun extractWorryLevelTransformer(input: String): (Long) -> Long {
    val parts = input.split(" ")
    val value = parts.last().toLongOrNull() ?: return fun(old: Long): Long { return old * old}
    return when {
        parts.dropLast(1).last() == "*" -> fun(old: Long): Long { return old * value}
        else -> fun(old: Long): Long { return old + value}
    }
}
