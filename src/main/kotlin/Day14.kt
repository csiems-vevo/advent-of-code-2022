import shared.rangeBetween

fun runSandSimulation(input: List<String>): Int {
    val cave = SandCave(input)

    val edges = listOf(
        cave.leftEdge(),
        cave.rightEdge()
    )

    return SandCave(input).runSandSimulator(edges)
}

fun runAdvancedSandSimulation(input: List<String>): Int {
    return SandCave(input, true).runSandSimulator(listOf(0)) + 1
}

class SandCave(input: List<String>, useFloor: Boolean = false) {

    private val barriers = buildBarriers(input, useFloor)
    val grid = buildGrid()

    private fun buildGrid(): Array<Array<Cell>> {
        val gridX = barriers.maxOfOrNull { it.x } ?: 0
        val gridY = barriers.maxOfOrNull { it.y } ?: 0

        return (0 .. gridY).map {y ->
            (0 .. gridX).map {x ->
                Cell(x, y)
            }.toTypedArray()
        }.toTypedArray()
    }

    private fun parseLine(line: String): List<Cell> {
        return line.split("->").map {
            val (x,y) = it.split(",")
            Cell(x.trim().toInt(), y.trim().toInt())
        }
    }

    private fun buildBarriers(input: List<String>, useFloor: Boolean = false): MutableList<Cell> {
        val barriers = input.flatMap { line ->
            val barrierInstruction = parseLine(line)
            barrierInstruction.zipWithNext().flatMap {
                it.first.barrierTo(it.second)
            }
        }.toMutableList()

        return if (useFloor) {
            barriers.withFloor()
        } else {
            barriers
        }
    }

    private fun MutableList<Cell>.withFloor(): MutableList<Cell> {
        val floorY = maxOfOrNull { it.y }?.plus(2) ?: 0
        val floorX = maxOfOrNull { it.x }?.plus(floorY) ?: 0

        this.addAll(
            (0 .. floorX)
                .map {x -> Cell(x, floorY) }
                .toTypedArray()
        )

        return this
    }

    fun runSandSimulator(endMarkers: List<Int>): Int {
        val start = Cell(500, 0)
        var current = start
        var sandCount = 0
        while (true) {
            val next = listOf(dropDown(current), dropLeft(current), dropRight(current)).firstOrNull { it !in barriers }
            current = when {
                next == null && current == Cell(500, 0) -> return sandCount
                next == null -> {
                    barriers.add(current)
                    sandCount += 1
                    start
                }

                endMarkers.contains(next.y) -> return sandCount
                else -> next
            }
        }
    }

    private fun dropDown(cell: Cell): Cell {
        return cell + Cell(0, 1)
    }

    private fun dropLeft(cell: Cell): Cell {
        return cell + Cell(-1, 1)
    }

    private fun dropRight(cell: Cell): Cell {
        return cell + Cell(1, 1)
    }

    fun leftEdge() = barriers.minOf { it.x } - 1
    fun rightEdge() = barriers.maxOf { it.x } + 1

    private fun Array<Array<Cell>>.prettyPrint() {
        val gridX = first().size
        val gridY = size

        println(buildString {
            (0 until gridY).map { y ->
                (0 until gridX).map { x ->
                    if (barriers.contains(Cell(x, y))) append('#') else append(".")
                }
                append("\n")
            }
        })
    }
}

operator fun Array<Array<Cell>>.get(cell: Cell): Cell = this[cell.y][cell.x]

operator fun Array<Array<Cell>>.set(from: Cell, to: Cell) { this[from.y][from.x] = to }

operator fun Array<Array<Cell>>.contains(cell: Cell): Boolean =
    cell.y in this.indices && cell.x in this[cell.y].indices

operator fun Cell.plus(other: Cell): Cell {
    return Cell(this.x + other.x, this.y + other.y)
}

data class Cell(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is Cell) return false
        return x == other.x && y == other.y
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun barrierTo(other: Cell): List<Cell> =
        if(other.x == x) {
            buildList {
                for(n in y rangeBetween other.y) {
                    add(Cell(x, n))
                }
            }
        } else {
            buildList {
                for(n in x rangeBetween other.x) {
                    add(Cell(n, y))
                }
            }
        }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

}



