import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day14Test {
    private val sampleData = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().lines()
    private val actualData = getFile("Day14").readLines()

    @Test
    fun `part 1 sample`() {
        val result = runSandSimulation(sampleData)
        result shouldBe 24
    }

    @Test
    fun `part 1`() {
        val result = runSandSimulation(actualData)
        result shouldBe 763
    }

    @Test
    fun `part 2 sample`() {
        val result = runAdvancedSandSimulation(sampleData)
        result shouldBe 93
    }

    @Test(enabled = false)
    fun `part 2`() {
        println("This test takes ~5 minutes")
        val result = runAdvancedSandSimulation(actualData)
        result shouldBe 23921
    }
}
