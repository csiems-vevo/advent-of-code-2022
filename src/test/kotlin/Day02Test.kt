import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day02Test {
    private val sampleData = listOf(Pair('A','Y'), Pair('B','X'), Pair('C','Z'))
    private val actualData = getFile("Day02").toCharCharPairList()

    @Test
    fun `part 1 sample`() {
        val result = playRound1RockPaperScissors(sampleData)
        result shouldBe 15
    }

    @Test
    fun `part 1`() {
        val result = playRound1RockPaperScissors(actualData)
        result shouldBe 15422
    }

    @Test
    fun `part 2 sample`() {
        val result = playRound2RockPaperScissors(sampleData)
        result shouldBe 12
    }

    @Test
    fun `part 2`() {
        val result = playRound2RockPaperScissors(actualData)
        result shouldBe 15442
    }
}
