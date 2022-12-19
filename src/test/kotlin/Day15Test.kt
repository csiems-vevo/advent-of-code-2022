import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day15Test {
    private val sampleData = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent().lines()
    private val actualData = getFile("Day15").readLines()

    @Test
    fun `part 1 sample`() {
        val result = findClearedCoordinatesByRow(10, sampleData)
        result shouldBe 26
    }

    @Test
    fun `part 1`() {
        val result = findClearedCoordinatesByRow(2_000_000, actualData)
        result shouldBe 5461729
    }

    @Test
    fun `part 2 sample`() {
        val result = findUnsensoredCoordinateFrequency(sampleData, 0, 20)
        result shouldBe 56000011L
    }

    @Test
    fun `part 2`() {
        val result = findUnsensoredCoordinateFrequency(actualData, 0, 4_000_000)
        result shouldBe 10621647166538L
    }
}
