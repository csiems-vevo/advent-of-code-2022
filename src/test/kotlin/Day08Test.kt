import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day08Test {
    private val sampleData = listOf(
        "30373",
        "25512",
        "65332",
        "33549",
        "35390"
    )
    private val actualData = getFile("Day08").readLines()

    @Test
    fun `part 1 sample`() {
        val result = findVisible(sampleData)
        result shouldBe 21
    }

    @Test
    fun `part 1`() {
        val result = findVisible(actualData)
        result shouldBe 1647
    }

    @Test
    fun `part 2 sample`() {
        val result = findBestScenicScore(sampleData)
        result shouldBe 8
    }

    @Test
    fun `part 2`() {
        val result = findBestScenicScore(actualData)
        result shouldBe 392080
    }
}
