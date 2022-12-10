import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day11Test {
    private val sampleData = listOf<String>()
    private val actualData = getFile("Day11").readLines()

    @Test
    fun `part 1 sample`() {
        val result = doStuff(sampleData)
        result shouldBe 0
    }

    @Test
    fun `part 1`() {
        val result = doStuff(actualData)
        result shouldBe 0
    }

    @Test
    fun `part 2 sample`() {
        val result = doStuff(sampleData)
        result shouldBe 0
    }

    @Test
    fun `part 2`() {
        val result = doStuff(actualData)
        result shouldBe 0
    }
}
