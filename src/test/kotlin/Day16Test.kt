import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day16Test {

    private val sampleData = """
            Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
            Valve BB has flow rate=13; tunnels lead to valves CC, AA
            Valve CC has flow rate=2; tunnels lead to valves DD, BB
            Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
            Valve EE has flow rate=3; tunnels lead to valves FF, DD
            Valve FF has flow rate=0; tunnels lead to valves EE, GG
            Valve GG has flow rate=0; tunnels lead to valves FF, HH
            Valve HH has flow rate=22; tunnel leads to valve GG
            Valve II has flow rate=0; tunnels lead to valves AA, JJ
            Valve JJ has flow rate=21; tunnel leads to valve II
        """.trimIndent().lines()
    private val actualData = getFile("Day16").readLines()

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
