import java.io.File

const val INPUT_PARENT_PATH = "src"
const val INPUT_CHILD_PATH = "test/resources/"
/**
 * Reads lines from the given input txt file.
 */
fun getFile(name: String) = File(INPUT_PARENT_PATH, "$INPUT_CHILD_PATH$name.txt")

fun File.toSpacedListOfIntLists() = this.readText().trim().split("\n\n")
    .map { elf ->
        elf.lines().map { calorie ->
            calorie.trim().toInt()
        }
    }
fun File.toCharCharPairList(): List<Pair<Char,Char>> = this.readLines().map { Pair(it[0],it[2]) }

//fun File.toIntList(): List<Int> = this.toStringList().map { it.toInt() }
//fun readInputAsOneLineIntList(name: String) = toStringList(name).flatMap { line -> line.split(",").map { it.toInt() } }
//fun readInputAsListOfIntList(name: String) = toStringList(name).map { it.map { char -> char.digitToInt() } }

/**
 * Get entire range of numbers between the min and max of a list
 */
fun List<Int>.range(): List<Int> {
    return (this.minOrNull()!!..this.maxOrNull()!!).toList()
}

fun <T, M> Iterable<T>.countBy(transformer: (T) -> M) : Map<M, Long> {
    val emptyMap = mapOf<M, Long>()
    return this.fold(emptyMap) {accumulator, item ->
        val transformedItem = transformer(item)

        accumulator + Pair(transformedItem, accumulator.getOrElse(transformedItem) { 0 } + 1)
    }
}


