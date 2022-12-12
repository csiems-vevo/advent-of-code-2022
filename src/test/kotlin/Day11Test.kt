import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day11Test {
    private val sampleData = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
            
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent().splitByNewLine()

    private val actualData = getFile("Day11").splitByNewline()

    @Test
    fun `part 1 sample`() {
        val result = runMonkeyProgram(20, sampleData, fun(value: Long): Long { return value / 3 })
        result shouldBe 10605
    }

    @Test
    fun `part 1`() {
        val result = runMonkeyProgram(20, actualData, fun(value: Long): Long { return value / 3 })
        result shouldBe 182293
    }

    @Test
    fun `part 2 sample`() {
        val result = runMonkeyProgram(10_000, sampleData)
        result shouldBe 2713310158
    }

    @Test
    fun `part 2`() {
        val result = runMonkeyProgram(10_000, actualData)
        result shouldBe 54832778815
    }
}
