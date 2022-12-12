import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day13Test {
    private val sampleData = """

    """.trimIndent().lines()
    private val actualData = getFile("Day13").readLines()

    @Test
    fun `part 1 sample`() {
        val result = doStuff(sampleData)
        result shouldBe 0
    }

//    @Test
//    fun `part 1`() {
//        val result = doStuff(actualData)
//        result shouldBe 0
//    }
//
//    @Test
//    fun `part 2 sample`() {
//        val result = doStuff(sampleData)
//        result shouldBe 0
//    }
//
//    @Test
//    fun `part 2`() {
//        val result = doStuff(actualData)
//        result shouldBe 0
//    }
}
