import com.google.common.collect.HashMultimap
import com.google.common.collect.SetMultimap
import shared.rangeBetween
import kotlin.math.abs

fun findDeadSpots(row: Int, input: List<String>): Int {
    val deadSpots = calculateDeadspots(row, input)
    return deadSpots.size
}


// For each point in a line, is it <= n distance from each sensor

private fun calculateDeadspots(row: Int, input: List<String>): MutableSet<Int> {

    val sensorBeacons = HashMultimap.create<Int,Int>()
    val deadSpots = mutableSetOf<Int>()

    input.forEach { line ->
        val (sensorX, sensorY, beaconX, beaconY) = line.split("Sensor at x=", ", y=", ": closest beacon is at x=").drop(1)
        val sensor = ValuePoint(sensorX.toInt(), sensorY.toInt())
        val beacon = ValuePoint(beaconX.toInt(), beaconY.toInt())
        sensorBeacons.put(sensorY.toInt(), sensorX.toInt())
        sensorBeacons.put(beaconY.toInt(), beaconX.toInt())
        val dist = sensor.manhattanDistTo(beacon.x, beacon.y)

        val nearby = sensor.pointsWithinDistance(dist, row).filterNotNull()

        deadSpots.addAll(nearby)
        println("Deadspots size: ${deadSpots.size}")
    }

    sensorBeacons.entries().filter { it.key == row }.forEach { entry ->
        deadSpots.remove(entry.value)
    }

    return deadSpots
}

private operator fun  SetMultimap<Int, Int>.contains(point: ValuePoint) = this.get(point.y).contains(point.x)

data class ValuePoint(val x: Int, val y: Int) {

    fun manhattanDistTo(otherX: Int, otherY: Int) = abs(x - otherX) + abs(y - otherY)

    fun pointsWithinDistance(dist: Int, row: Int): Array<Int?> {
        val minX = x - dist
        val maxX = x + dist

        val points = arrayOfNulls<Int>(5_000_000)

        (minX rangeBetween maxX).map { x ->
            if (this.manhattanDistTo(x, row) <= dist) {
                val next = points.indexOfFirst { it == null }
                points[next] = x
            }
        }
        return points
    }
}
