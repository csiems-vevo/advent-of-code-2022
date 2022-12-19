import com.google.common.collect.SetMultimap
import shared.Point2d
import shared.rangeBetween
import shared.plus

fun findClearedCoordinatesByRow(row: Int, input: List<String>): Int {
    return efficientClearedCounter(row, input)
}

fun findUnsensoredCoordinateFrequency(input: List<String>, minBoundary: Int, maxBoundary: Int): Long {
    val myCoordinate = calculateUnsensoredCoordinates(input, minBoundary, maxBoundary)
    return (myCoordinate.x.toLong().times(4_000_000L)).plus(myCoordinate.y.toLong())
}

private fun efficientClearedCounter(row: Int, input: List<String>): Int {

    val beacons = parseBeacons(input)
    val sensors = parseSensors(input)
    val maxDist = sensors.maxOf { it.distance }
    val minX = sensors.minOf { it.coordinate.x } - maxDist
    val maxX = sensors.maxOf { it.coordinate.x } + maxDist

    return countClearedCoordinates(minX, maxX, row, beacons, sensors)
}

private fun countClearedCoordinates(
    minX: Int,
    maxX: Int,
    row: Int,
    beacons: List<Point2d>,
    sensors: List<Sensor>
): Int {
    var clearedCount = 0
    for (x in minX rangeBetween maxX) {
        val pointInLine = Point2d(x, row)
        if (beacons.none { it == pointInLine }) {
            val isCleared = sensors.any { sensor ->
                sensor.coordinate.isWithinDistance(pointInLine, sensor.distance)
            }
            if (isCleared) clearedCount++
        }
    }
    return clearedCount
}

fun parseBeacons(input: List<String>): List<Point2d> {
    return input.map { line ->
        val (_, _, beaconX, beaconY) = line.split("Sensor at x=", ", y=", ": closest beacon is at x=").drop(1)
        Point2d(beaconX.toInt(), beaconY.toInt())
    }
}

fun parseSensors(input: List<String>): List<Sensor> {
    return input.map { line ->
        val (sensorX, sensorY, beaconX, beaconY) = line.split("Sensor at x=", ", y=", ": closest beacon is at x=").drop(1)
        val sensorCoord = Point2d(sensorX.toInt(), sensorY.toInt())
        val beacon = Point2d(beaconX.toInt(), beaconY.toInt())
        val dist = sensorCoord.manhattanDistTo(beacon.x, beacon.y)
        Sensor(sensorCoord, dist)
    }
}

fun calculateUnsensoredCoordinates(input: List<String>, minBoundary: Int, maxBoundary: Int): Point2d {
    val sensors = parseSensors(input)

    return sensors.mapNotNull { sensor ->
        val outerDistance = sensor.distance + 1
        val outerUpper = sensor.coordinate + Point2d(0, -outerDistance)
        val outerLeft = sensor.coordinate + Point2d(-outerDistance, 0)
        val outerLower = sensor.coordinate + Point2d(0, outerDistance)
        val outerRight = sensor.coordinate + Point2d(outerDistance, 0)
        val acceptableRange = (minBoundary..maxBoundary)

        (outerUpper.lineTo(outerLeft) + outerLeft.lineTo(outerLower) + outerLower.lineTo(outerRight) + outerRight.lineTo(outerUpper))
            .toSet()
            .filter {
                it.x in acceptableRange && it.y in acceptableRange
            }
            .firstOrNull {
                sensors.none { sensor ->
                    sensor.coordinate.isWithinDistance(it, sensor.distance)
                }
            }
    }.first()
}

data class Sensor(val coordinate: Point2d, val distance: Int)

private operator fun  SetMultimap<Int, Int>.contains(point: Point2d) = this.get(point.y).contains(point.x)

fun Point2d.isWithinDistance(other: Point2d, dist: Int): Boolean = this.manhattanDistTo(other) <= dist

