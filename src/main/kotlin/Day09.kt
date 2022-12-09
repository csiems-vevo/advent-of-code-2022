import shared.Point2d
import kotlin.math.abs

fun countTwoKnotRopeTailPositions(input: List<String>): Int {
    clearFields()
    input.forEach { processTwoKnotCommand(it) }
    return pastTailPositions.size
}

fun countTenKnotRopeTailPositions(input: List<String>): Int {
    clearFields()
    input.forEach { processTenKnotCommand(it) }
    return pastTailPositions.size
}

var pastTailPositions = mutableSetOf(Point2d(0,0))
var currentHeadPosition = Point2d(0,0)
var current1Position = Point2d(0,0)
var current2Position = Point2d(0,0)
var current3Position = Point2d(0,0)
var current4Position = Point2d(0,0)
var current5Position = Point2d(0,0)
var current6Position = Point2d(0,0)
var current7Position = Point2d(0,0)
var current8Position = Point2d(0,0)
var currentTailPosition = Point2d(0,0)

fun processTwoKnotCommand(command: String) {
    val (direction, times) = command.split(" ")
    when(direction) {
        "R" -> {
            repeat(times.toInt()) {
                currentHeadPosition = move(currentHeadPosition, Point2d(1,0))
                currentTailPosition = moveFollower(currentHeadPosition, currentTailPosition, true)
            }
        }
        "L" -> {
            repeat(times.toInt()) {
                currentHeadPosition = move(currentHeadPosition, Point2d(-1,0))
                currentTailPosition = moveFollower(currentHeadPosition, currentTailPosition, true)
            }
        }
        "U" -> {
            repeat(times.toInt()) {
                currentHeadPosition = move(currentHeadPosition, Point2d(0,1))
                currentTailPosition = moveFollower(currentHeadPosition, currentTailPosition, true)
            }
        }
        "D" -> {
            repeat(times.toInt()) {
                currentHeadPosition = move(currentHeadPosition, Point2d(0,-1))
                currentTailPosition = moveFollower(currentHeadPosition, currentTailPosition, true)
            }
        }
        else -> throw IllegalArgumentException("Bad command in instructions")
    }
}

fun processTenKnotCommand(command: String) {
    val (direction, times) = command.split(" ")
    when(direction) {
        "R" -> {
            repeat(times.toInt()) {
                currentHeadPosition = move(currentHeadPosition, Point2d(1,0))
                moveTenKnots()
            }
        }
        "L" -> {
            repeat(times.toInt()) {
                currentHeadPosition = move(currentHeadPosition, Point2d(-1,0))
                moveTenKnots()
            }
        }
        "U" -> {
            repeat(times.toInt()) {
                currentHeadPosition = move(currentHeadPosition, Point2d(0,1))
                moveTenKnots()
            }
        }
        "D" -> {
            repeat(times.toInt()) {
                currentHeadPosition = move(currentHeadPosition, Point2d(0,-1))
                moveTenKnots()
            }
        }
        else -> throw IllegalArgumentException("Bad command in instructions")
    }
}

private fun moveTenKnots() {
    current1Position = moveFollower(currentHeadPosition, current1Position)
    current2Position = moveFollower(current1Position, current2Position)
    current3Position = moveFollower(current2Position, current3Position)
    current4Position = moveFollower(current3Position, current4Position)
    current5Position = moveFollower(current4Position, current5Position)
    current6Position = moveFollower(current5Position, current6Position)
    current7Position = moveFollower(current6Position, current7Position)
    current8Position = moveFollower(current7Position, current8Position)
    currentTailPosition = moveFollower(current8Position, currentTailPosition, true)
}


fun moveFollower(currentLeadingPosition: Point2d, currentFollowingPosition: Point2d, trackChanges: Boolean = false): Point2d {

    val diff = currentLeadingPosition.diff(currentFollowingPosition)

    val newFollowingPosition = if(diff.distance > 1) {
        move(currentFollowingPosition, diff)
    } else {
        currentFollowingPosition
    }

    if (trackChanges) {
        pastTailPositions.add(newFollowingPosition)
    }
    return newFollowingPosition
}


fun Point2d.moveNorth() = move(this, Point2d(0, 1))
fun Point2d.moveSouth() = move(this, Point2d(0, -1))
fun Point2d.moveWest() = move(this, Point2d(-1, 0))
fun Point2d.moveEast() = move(this, Point2d(1, 0))
fun Point2d.moveNortheast() = move(this, Point2d(1, 1))
fun Point2d.moveNorthwest() = move(this, Point2d(-1, 1))
fun Point2d.moveSoutheast() = move(this, Point2d(1, -1))
fun Point2d.moveSouthwest() = move(this, Point2d(-1, -1))

fun move(currentLocation: Point2d, relativeLocation: Point2d): Point2d {
    return currentLocation + relativeLocation
}

private fun Point2d.diff(other: Point2d): Point2d {
    return this - other
}

private operator fun Point2d.plus(other: Point2d): Point2d {
    return Point2d(this.x + (other.x.coerceIn(-1,1)), this.y + (other.y.coerceIn(-1,1)))
}

private operator fun Point2d.minus(other: Point2d): Point2d {
    return Point2d(this.x - other.x, this.y - other.y)
}

val Point2d.distance: Int get() = maxOf(abs(x), abs(y))

fun clearFields() {
    pastTailPositions = mutableSetOf(Point2d(0,0))
    currentHeadPosition = Point2d(0,0)
    current1Position = Point2d(0,0)
    current2Position = Point2d(0,0)
    current3Position = Point2d(0,0)
    current4Position = Point2d(0,0)
    current5Position = Point2d(0,0)
    current6Position = Point2d(0,0)
    current7Position = Point2d(0,0)
    current8Position = Point2d(0,0)
    currentTailPosition = Point2d(0,0)
}
