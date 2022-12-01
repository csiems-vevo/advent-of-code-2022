import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day01Test {

    private val sampleData = listOf(listOf(1000,2000,3000), listOf(4000), listOf(5000,6000), listOf(7000, 8000, 9000), listOf(10000))
    private val actualData = getFile("Day01").toSpacedListOfIntLists()

    @Test
    fun `part 1 sample`() {
        val result = calculateMaxElfCalories(sampleData)
        result shouldBe 24000
    }

    @Test
    fun `part 1`() {
        val result = calculateMaxElfCalories(actualData)
        result shouldBe 65912
    }

    @Test
    fun `part 2 sample`() {
        val result = calculateTopNElfCalories(3, sampleData)
        result shouldBe 45000
    }

    @Test
    fun `part 2`() {
        val result = calculateTopNElfCalories(3, actualData)
        result shouldBe 195625
    }
}
