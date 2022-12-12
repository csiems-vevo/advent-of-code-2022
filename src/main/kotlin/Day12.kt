import shared.Point2d


// Breadth First Search through Array<CharArray>
class Day12(input: List<String>) {

    private val parsed = parseInput(input)
    private val start = parsed.first
    private val end = parsed.second
    private val grid = parsed.third

    fun shortestPathStepsFromStart(): Int {
        val path = grid.bfs(start, end)
        return path.size - 1
    }

    fun shortestPathFromCharA(): Int {
        return grid.mapIndexed { y, chars ->
            chars.mapIndexed { x, c ->
                if (c == 'a') {
                    Point2d(x, y)
                } else {
                    null
                }
            }
        }
            .flatten()
            .filterNotNull()
            .map { grid.bfs(it, end).count() - 1 }
            .filterNot { it == 0 } // bfs can return empty list if no path found
            .minOf { it }
    }

    private fun parseInput(input: List<String>): Triple<Point2d, Point2d, Array<CharArray>> {
        var start = Point2d(0, 0)
        var end = Point2d(0, 0)
        val values = input.mapIndexed { y, row ->
            row.mapIndexed { x, c ->
                when(c) {
                    // recording start and end then setting to 'a' and 'z' respectively
                    'S' -> {
                        start = Point2d(x, y)
                        'a'
                    }
                    'E' -> {
                        end = Point2d(x, y)
                        'z'
                    }
                    else -> c
                }
            }.toCharArray()
        }.toTypedArray()

        return Triple(start, end, values)
    }

    // returns the shortest path
    private fun Array<CharArray>.bfs(start: Point2d, end: Point2d): List<Point2d> {
        assert(start in this)
        assert(end in this)

        val path = mutableMapOf<Point2d, Point2d>()
        val queue = mutableListOf<Point2d>()
        val visited = mutableMapOf<Point2d, Boolean>()
        queue.add(start)
        visited[start] = true

        while (queue.size > 0) {
            val curr = queue.removeLast()
            // short circuit if we find end earlier
            if (curr == end) break
            validMoves.mapNotNull { direction ->
                val newPoint = Point2d(curr.x + direction.x, curr.y + direction.y)
                if (newPoint !in this) {
                    null
                } else {
                    newPoint
                }
            }.filter { current ->
                !visited.getOrDefault(current, false)
            }.filter { point ->
                curr.isValid(point)
            }.forEach { next ->
                queue.add(0, next)
                path[next] = curr
                visited[next] = true
            }
        }

        // WARNING: List can be empty if complete path not found!
        return path.regeneratePath(end)
    }

    private fun Map<Point2d, Point2d>.regeneratePath(tail: Point2d): List<Point2d> {
        return when (val next = this[tail]) {
            null -> return listOf<Point2d>() + tail
            else -> regeneratePath(next) + tail
        }
    }

    operator fun Array<CharArray>.get(point: Point2d): Char = this[point.y][point.x]

    operator fun Array<CharArray>.contains(point: Point2d): Boolean =
        point.y in this.indices && point.x in this[point.y].indices

    private fun Point2d.isValid(other: Point2d): Boolean = grid[this] + 1 >= grid[other]

    private val validMoves = listOf(Point2d(1, 0), Point2d(-1, 0), Point2d(0, 1), Point2d(0, -1))
}
