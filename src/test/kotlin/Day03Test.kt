import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day03Test {
    private val sampleData = listOf(
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw"
    )
    private val actualData = getFile("Day03").readLines()

    @Test
    fun `part 1 sample`() {
        val result = calculateMisplacedItemPrioritySum(sampleData)
        result shouldBe 157
    }

    @Test
    fun `part 1`() {
        val result = calculateMisplacedItemPrioritySum(actualData)
        result shouldBe 8349
    }

    @Test
    fun `part 2 sample`() {
        val result = calculateBadgeItemPrioritySum(sampleData)
        result shouldBe 70
    }

    @Test
    fun `part 2`() {
        val result = calculateBadgeItemPrioritySum(actualData)
        result shouldBe 2681
    }
}
