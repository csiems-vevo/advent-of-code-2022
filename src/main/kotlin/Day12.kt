import shared.Point2d

// Breadth First Search through Array<CharArray>
class BreadthFirstSearcher(input: List<String>) {

    private val parsed = parseInput(input)
    private val start = parsed.first
    private val end = parsed.second
    private val grid = parsed.third

    fun shortestPathStepsFromStart(): Int {
        val path = grid.bfs(end) { it == start }
        return path.size - 1
    }

    fun shortestPathFromCharA(): Int {
        // starting at the destination means we only have to find one shortest path
        // rather than loop through all the possible starting positions
        val path = grid.bfs(end) { grid[it] == 'a'}
        return path.size - 1
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
    private fun Array<CharArray>.bfs(start: Point2d, isDestination: (Point2d) -> Boolean): List<Point2d> {
        assert(start in this)

        val path = mutableMapOf<Point2d, Point2d>()
        val processingQueue = mutableListOf<Point2d>()
        val visited = mutableMapOf<Point2d, Boolean>()
        var end = Point2d(0,0)
        processingQueue.add(start)
        visited[start] = true

        while (processingQueue.size > 0) {
            val curr = processingQueue.removeLast()
            // short circuit if we find end earlier
            if (isDestination(curr)) {
                end = curr
                break
            }
            validMoves.mapNotNull { direction ->
                Point2d(curr.x + direction.x, curr.y + direction.y)
                    .takeIf { it in this }
            }.filter { current ->
                !visited.getOrDefault(current, false)
            }.filter { point ->
                curr.isValidNeighborOf(point)
            }.forEach { next ->
                processingQueue.add(0, next)
                path[next] = curr
                visited[next] = true
            }
        }

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

    // since we are working backwards, a valid neighbor must be less than or equal to a point
    private fun Point2d.isValidNeighborOf(other: Point2d): Boolean = grid[this] <= grid[other] + 1

    private val validMoves = listOf(Point2d(1, 0), Point2d(-1, 0), Point2d(0, 1), Point2d(0, -1))
}
