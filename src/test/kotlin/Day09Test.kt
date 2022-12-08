import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day09Test {
    private val sampleData = listOf("")
    private val actualData = getFile("Day09").readLines()

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
