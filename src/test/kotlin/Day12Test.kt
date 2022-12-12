import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day12Test {
    private val sampleData = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent().lines()
    private val actualData = getFile("Day12").readLines()

    @Test
    fun `part 1 sample`() {
        val result = Day12(sampleData).shortestPathStepsFromStart()
        result shouldBe 31
    }

    @Test
    fun `part 1`() {
        val result = Day12(actualData).shortestPathStepsFromStart()
        result shouldBe 528
    }

    @Test
    fun `part 2 sample`() {
        val result = Day12(sampleData).shortestPathFromCharA()
        result shouldBe 29
    }

    @Test
    fun `part 2`() {
        val result = Day12(actualData).shortestPathFromCharA()
        result shouldBe 522
    }
}
