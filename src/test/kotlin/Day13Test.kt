import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day13Test {
    private val sampleData = """
        [1,1,3,1,1]
        [1,1,5,1,1]
        
        [[1],[2,3,4]]
        [[1],4]
        
        [9]
        [[8,7,6]]
        
        [[4,4],4,4]
        [[4,4],4,4,4]
        
        [7,7,7,7]
        [7,7,7]
        
        []
        [3]
        
        [[[]]]
        [[]]
        
        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent().lines()
    private val actualData = getFile("Day13").readLines()

    @Test
    fun `part 1 sample`() {
        val result = countCorrectlyOrderedPairs(sampleData)
        result shouldBe 13
    }

    @Test
    fun `part 1`() {
        val result = countCorrectlyOrderedPairs(actualData)
        result shouldBe 6240
    }

    @Test
    fun `part 2 sample`() {
        val result = findDecoderKey(sampleData)
        result shouldBe 140
    }

    @Test
    fun `part 2`() {
        val result = findDecoderKey(actualData)
        result shouldBe 23142
    }
}
