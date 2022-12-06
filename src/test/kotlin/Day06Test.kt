import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day06Test {
    private val sampleData = mapOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to Pair(7,19),
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to Pair(5,23),
        "nppdvjthqldpwncqszvftbrmjlhg" to Pair(6,23),
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to Pair(10,29),
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to Pair(11,26)
        )
    private val actualData = getFile("Day06").readText()

    @Test
    fun `part 1 sample`() {
        sampleData.keys.forEach {
            val result = findPacketMarker(it)
            result shouldBe sampleData[it]!!.first
        }
    }

    @Test
    fun `part 1`() {
        val result = findPacketMarker(actualData)
        result shouldBe 1965
    }

    @Test
    fun `part 2 sample`() {
        sampleData.keys.forEach {
            val result = findMessageMarker(it)
            result shouldBe sampleData[it]!!.second
        }
    }

    @Test
    fun `part 2`() {
        val result = findMessageMarker(actualData)
        result shouldBe 2773
    }
}
