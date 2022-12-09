import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day09Test {
    private val sampleData = listOf(
        "R 4",
        "U 4",
        "L 3",
        "D 1",
        "R 4",
        "D 1",
        "L 5",
        "R 2"
    )
    private val largerSampleData = listOf(
        "R 5",
        "U 8",
        "L 8",
        "D 3",
        "R 17",
        "D 10",
        "L 25",
        "U 20"
    )
    private val actualData = getFile("Day09").readLines()

    @Test
    fun `part 1 sample`() {
        val result = countTwoKnotRopeTailPositions(sampleData)
        result shouldBe 13
    }

    @Test
    fun `part 1`() {
        val result = countTwoKnotRopeTailPositions(actualData)
        result shouldBe 5874
    }

    @Test
    fun `part 2 sample`() {
        val result = countTenKnotRopeTailPositions(sampleData)
        result shouldBe 1
    }

    @Test
    fun `part 2 larger sample`() {
        val result = countTenKnotRopeTailPositions(largerSampleData)
        result shouldBe 36
    }

    @Test
    fun `part 2`() {
        val result = countTenKnotRopeTailPositions(actualData)
        result shouldBe 2467
    }
}
