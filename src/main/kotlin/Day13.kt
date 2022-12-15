import shared.product

fun countCorrectlyOrderedPairs(input: List<String>): Int {
    val data = parseCompoundNumbers(input)
    val indexList = mutableListOf<Int>()
    data.chunked(2).forEachIndexed { index, it ->
        if(it.first() <= it.last()) {
            indexList.add(index + 1)
        }
    }
    return indexList.sum()
}

fun findDecoderKey(input: List<String>): Int {
    val dividerPackets = parseCompoundNumbers(listOf("[[2]]", "[[6]]"))
    val data = (parseCompoundNumbers(input) + dividerPackets).sorted()
    return dividerPackets.mapIndexed { index, s ->
        data.indexOf(s) + 1
    }.product()

}

fun parseCompoundNumbers(input: List<String>): List<CompoundNumber> {
    return input.filter { it.isNotBlank() }
        .map { CompoundNumber.of(it) }
}

sealed class CompoundNumber : Comparable<CompoundNumber> {
    companion object {
        fun of(input: String): CompoundNumber =
            of(
                input.split("""((?<=[\[\],])|(?=[\[\],]))""".toRegex())
                    .filter { it.isNotBlank() }
                    .filter { it != "," }
                    .iterator()
            )

        private fun of(input: Iterator<String>): CompoundNumber {
            val numbers = mutableListOf<CompoundNumber>()

            while (input.hasNext()) {
                when (val symbol = input.next()) {
                    "]" -> return ListyCompoundNumber(numbers)
                    "[" -> numbers.add(of(input))
                    else -> numbers.add(IntyCompoundNumber(symbol.toInt()))
                }
            }
            return ListyCompoundNumber(numbers)
        }
    }
}

data class IntyCompoundNumber(val value: Int) : CompoundNumber() {
    fun toListyCompoundNumber() = ListyCompoundNumber(listOf(IntyCompoundNumber(value)))

    override fun compareTo(other: CompoundNumber): Int {
        return when(other) {
            is IntyCompoundNumber -> value.compareTo(other.value)
            // Mixed types; convert left to listy and retry comparison
            is ListyCompoundNumber -> toListyCompoundNumber().compareTo(other)
        }
    }
}

data class ListyCompoundNumber(val values: List<CompoundNumber>) : CompoundNumber() {
    override fun compareTo(other: CompoundNumber): Int {
        return when(other) {
            is IntyCompoundNumber -> compareTo(other.toListyCompoundNumber())
            is ListyCompoundNumber -> values.zip(other.values)
                .map {
                    it.first.compareTo(it.second)
                }.firstOrNull { it != 0 } ?: values.size.compareTo(other.values.size)
        }
    }
}
