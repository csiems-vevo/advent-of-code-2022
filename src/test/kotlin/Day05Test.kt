import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day05Test {
    private val sampleData = """
            [D]
        [N] [C]
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent()
    private val actualData = getFile("Day05").readText()

    @Test
    fun `part 1 sample`() {
        val result = useCrane9000(sampleData)
        result shouldBe "CMZ"
    }

    @Test
    fun `part 1`() {
        val result = useCrane9000(actualData)
        result shouldBe "BSDMQFLSP"
    }

    @Test
    fun `part 2 sample`() {
        val result = useCrane9001(sampleData)
        result shouldBe "MCD"
    }

    @Test
    fun `part 2`() {
        val result = useCrane9001(actualData)
        result shouldBe "PGSQBFLDP"
    }
}
