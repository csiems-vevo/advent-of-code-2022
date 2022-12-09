import shared.Point2d
import kotlin.math.abs

fun countTwoKnotRopeTailPositions(input: List<String>): Int {
    return processInfiniteKnotCommands(2, input)
}

fun countTenKnotRopeTailPositions(input: List<String>): Int {
    return processInfiniteKnotCommands(10, input)
}

fun processInfiniteKnotCommands(knots: Int, commands: List<String>): Int {

    val recordedTailPositions = mutableSetOf(Point2d(0,0))
    val knotPositions = MutableList(knots) { Point2d(0,0)}

    commands.forEach { command ->
        val (direction, times) = command.split(" ")

        repeat(times.toInt()) {
            knotPositions[0] = move(knotPositions[0], direction.toDirection())
            for ((leader, follower) in knotPositions.indices.zipWithNext()) {
                knotPositions[follower] = moveFollower(knotPositions[leader], knotPositions[follower])
            }
            recordedTailPositions += knotPositions.last()
        }
    }
    return recordedTailPositions.size
}

private enum class Move(val direction: Point2d) {
    R(Point2d(1,0)),
    L(Point2d(-1,0)),
    U(Point2d(0,1)),
    D(Point2d(0,-1))
}

fun moveFollower(currentLeadingPosition: Point2d, currentFollowingPosition: Point2d): Point2d {
    val diff = currentLeadingPosition - currentFollowingPosition

    val newFollowingPosition = if(diff.distance > 1) {
        move(currentFollowingPosition, diff)
    } else {
        currentFollowingPosition
    }

    return newFollowingPosition
}

fun move(currentLocation: Point2d, relativeLocation: Point2d): Point2d {
    return currentLocation + relativeLocation
}

private operator fun Point2d.plus(other: Point2d): Point2d {
    return Point2d(this.x + (other.x.coerceIn(-1,1)), this.y + (other.y.coerceIn(-1,1)))
}

private operator fun Point2d.minus(other: Point2d): Point2d {
    return Point2d(this.x - other.x, this.y - other.y)
}

private fun String.toDirection() = Move.valueOf(this).direction


val Point2d.distance: Int get() = maxOf(abs(x), abs(y))


