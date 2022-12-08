import shared.Point2d

fun findVisible(input: List<String>): Int {
    return ForestGrid(input).findVisible().size
}

fun findBestScenicScore(input: List<String>): Int {
    return ForestGrid(input).findBestScenicScore()
}

class ForestGrid(input: List<String>) {

    private val grid = parseInput(input)

    private fun parseInput(input: List<String>): Array<IntArray> =
        input.map { row ->
            row.map { it.digitToInt() }.toIntArray()
        }.toTypedArray()

    fun findBestScenicScore(): Int {
        return grid.points().maxOfOrNull { it.scenicScore() } ?: 0
    }

    fun findVisible(): Set<Point2d> {
        return (grid.flatMapIndexed { y, row ->
            row.mapIndexed { x, _ ->
                Point2d(x, y).takeIf { point ->
                    point.isVisibleVertically() || point.isVisibleHorizontally()
                }
            }.filterNotNull()
        } + grid.outers()).toSet()
    }

    private fun traverseUntilBlocked(start: Point2d, paths: Map<Boolean, List<Point2d>>): List<Int> {
        val pathLengths = mutableListOf<Int>()
        val currentPath = mutableListOf<Point2d>()

        paths.keys.forEach { key ->
            val path = paths[key]!!.toMutableList()
            path.remove(start)

            if(key) {
                traverse(path, start, currentPath, pathLengths, false)
            } else {
                traverse(path, start, currentPath, pathLengths, true)
            }
        }
        return pathLengths
    }

    private fun traverse(
        path: MutableList<Point2d>,
        start: Point2d,
        currentPath: MutableList<Point2d>,
        pathLengths: MutableList<Int>,
        reversed: Boolean
    ) {
        val indices = if(reversed) { path.indices.reversed() } else { path.indices}
        for (i in indices) {
            val currentPoint = path[i]
            if (grid[currentPoint] < grid[start] && !currentPoint.isEdge()) {
                currentPath.add(currentPoint)
            } else {
                currentPath.add(currentPoint)
                pathLengths.add(currentPath.size)
                currentPath.clear()
                break
            }
        }
    }

    // grid extensions
    fun Array<IntArray>.points(): List<Point2d> {
        return this.flatMapIndexed { y, row ->
            row.mapIndexed { x, _ ->
                Point2d(x, y)
            }
        }
    }

    private fun Array<IntArray>.outers(): List<Point2d> {
        return this.flatMapIndexed { y, row ->
            row.mapIndexed { x, _ ->
                Point2d(x, y).takeIf { point ->
                    point.x == 0 || point.x == this[0].size - 1 || point.y == 0 || point.y == this.size - 1
                }
            }.filterNotNull()
        }
    }




    // point extensions
    private fun Point2d.isEdge() = grid.outers().contains(this)

    private fun Point2d.scenicScore(): Int {
        val horizontalScores = this.allinRow()
            .groupBy { it.x > this.x }
        val verticalScores = this.allinColumn()
            .groupBy { it.y > this.y }
        return (traverseUntilBlocked(this, horizontalScores) + traverseUntilBlocked(this, verticalScores))
            .reduce { acc, i -> acc * i }
    }

    private fun Point2d.isVisibleHorizontally(): Boolean {
        return this.othersInRow()
            .groupBy { it.x > this.x }
            .any { neighbors -> neighbors.value.all { grid[it] < grid[this] } }
    }

    private fun Point2d.isVisibleVertically(): Boolean {
        return this.othersInColumn()
            .groupBy { it.y > this.y }
            .any { neighbors -> neighbors.value.all { grid[it] < grid[this] } }
    }

    private fun Point2d.allinColumn(): List<Point2d> {
        return grid.flatMapIndexed { y, row ->
            row.mapIndexed { x, _ ->
                Point2d(x, y).takeIf { point ->
                    point.x == this.x
                }
            }.filterNotNull()
        }
    }

    private fun Point2d.allinRow(): List<Point2d> {
        return grid.flatMapIndexed { y, row ->
            row.mapIndexed { x, _ ->
                Point2d(x, y).takeIf { point ->
                    point.y == this.y
                }
            }.filterNotNull()
        }
    }

    private fun Point2d.othersInColumn(): List<Point2d> {
        return this.allinColumn().filterNot { it.y == this.y }
    }

    private fun Point2d.othersInRow(): List<Point2d> {
        return this.allinRow().filterNot { it.x == this.x }
    }

    // operators
    operator fun Array<IntArray>.get(point: Point2d): Int =
        this[point.y][point.x]

    operator fun Array<IntArray>.contains(point: Point2d): Boolean =
        point.y in this.indices && point.x in this[point.y].indices

}



