package shared

import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

data class Point2d(val x: Int, val y: Int) {

    infix fun sharesAxisWith(that: Point2d): Boolean =
        x == that.x || y == that.y

    infix fun lineTo(that: Point2d): List<Point2d> {
        val xDelta = (that.x - x).sign
        val yDelta = (that.y - y).sign
        val steps = maxOf((x - that.x).absoluteValue, (y - that.y).absoluteValue)
        return (1..steps).scan(this) { last, _ -> Point2d(last.x + xDelta, last.y + yDelta) }
    }

    fun manhattanDistTo(otherX: Int, otherY: Int) = abs(x - otherX) + abs(y - otherY)
    fun manhattanDistTo(other: Point2d) = abs(x - other.x) + abs(y - other.y)

    fun isAdjacent(other: Point2d) = this.manhattanDistTo(other) == 1

    fun neighbors(): List<Point2d> = verticalNeighbors() + horizontalNeighbors()

    fun verticalNeighbors(): List<Point2d> =
        listOf(
            Point2d(x, y + 1),
            Point2d(x, y - 1)
        )

    fun horizontalNeighbors(): List<Point2d> =
        listOf(
            Point2d(x + 1, y),
            Point2d(x - 1, y)
        )

    fun allNeighbors(): List<Point2d> =
        neighbors() + listOf(
            Point2d(x - 1, y - 1),
            Point2d(x - 1, y + 1),
            Point2d(x + 1, y - 1),
            Point2d(x + 1, y + 1)
        )
}

fun Array<Array<Point2d>>.adjacencies(neighborSelector: Point2d.() -> List<Point2d>): Map<Point2d, List<Point2d>> {
    return this.flatten()
        .associateWith { point ->
            point.neighborSelector().filter { it in this }
        }
}

operator fun Array<Array<Point2d>>.get(point: Point2d): Point2d = this[point.y][point.x]

operator fun Array<Array<Point2d>>.contains(point: Point2d): Boolean =
    point.y in this.indices && point.x in this[point.y].indices

operator fun Point2d.plus(other: Point2d): Point2d {
    return Point2d(this.x + other.x, this.y + other.y)
}

private operator fun Point2d.minus(other: Point2d): Point2d {
    return Point2d(this.x - other.x, this.y - other.y)
}
