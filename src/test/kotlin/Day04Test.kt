import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day04Test {
    private val sampleData = listOf(
        Pair(2..4, 6..8),
        Pair(2..3, 4..5),
        Pair(5..7, 7..9),
        Pair(2..8, 3..7),
        Pair(6..6, 4..6),
        Pair(2..6, 4..8)
    )
    private val actualData = getFile("Day04").toIntRangeIntRangePairList()

    @Test
    fun `part 1 sample`() {
        val result = countFullyOverlappingPairs(sampleData)
        result shouldBe 2
    }

    @Test
    fun `part 1`() {
        val result = countFullyOverlappingPairs(actualData)
        result shouldBe 657
    }

    @Test
    fun `part 2 sample`() {
        val result = countPartialOverlappingPairs(sampleData)
        result shouldBe 4
    }

    @Test
    fun `part 2`() {
        val result = countPartialOverlappingPairs(actualData)
        result shouldBe 938
    }
}
