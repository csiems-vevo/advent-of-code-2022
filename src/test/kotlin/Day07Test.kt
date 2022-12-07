import io.kotest.matchers.shouldBe
import org.testng.annotations.Test

class Day07Test {
    private val sampleData = listOf(
        "$ cd /",
        "$ ls",
        "dir a",
        "14848514 b.txt",
        "8504156 c.dat",
        "dir d",
        "$ cd a",
        "$ ls",
        "dir e",
        "29116 f",
        "2557 g",
        "62596 h.lst",
        "$ cd e",
        "$ ls",
        "584 i",
        "$ cd ..",
        "$ cd ..",
        "$ cd d",
        "$ ls",
        "4060174 j",
        "8033020 d.log",
        "5626152 d.ext",
        "7214296 k"
    ).drop(1)
    private val actualData = getFile("Day07").readLines().drop(1)

    @Test
    fun `part 1 sample`() {
        val result = sumSmallDirectories(sampleData)
        result shouldBe 95437L
    }

    @Test
    fun `part 1`() {
        val result = sumSmallDirectories(actualData)
        result shouldBe 1367870L
    }

    @Test
    fun `part 2 sample`() {
        val result = findSmallestDirectoryNecessary(sampleData)
        result shouldBe 24933642
    }

    @Test
    fun `part 2`() {
        val result = findSmallestDirectoryNecessary(actualData)
        result shouldBe 549173
    }
}
