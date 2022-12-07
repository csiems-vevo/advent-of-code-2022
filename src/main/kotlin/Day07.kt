
fun sumSmallDirectories(input: List<String>): Long {
    val allDirectories = parseCommands(input)
    return allDirectories.filter { it.size <= 100_000 }.sumOf { it.size }
}

fun findSmallestDirectoryNecessary(input: List<String>): Long {
    val allDirectories = parseCommands(input).sortedBy { it.size }
    val memoryCurrentlyAvailable = 70000000L - allDirectories.last().size
    val memoryNeeded = 30000000L
    return allDirectories.first { it.size >= (memoryNeeded - memoryCurrentlyAvailable) }.size
}

private fun parseCommands(input: List<String>): MutableSet<ElfDirectory> {

    val rootDirectory = ElfDirectory("/", null)
    val allDirectories = mutableSetOf<ElfDirectory>().also { it.add(rootDirectory) }

    var currentDirectory = rootDirectory

    input.forEachIndexed { index, line ->
        val parts = line.split(" ")
        val command = parts[1]
        val location by lazy { parts[2] }

        when (command) {
            "ls" -> {
                var nextIndex = index + 1
                while (nextIndex < input.size && input[nextIndex][0] != '$') {
                    val item = input[nextIndex]
                    item.split(" ").let { (sizeOrDir, name) ->
                        when (sizeOrDir) {
                            "dir" -> {
                                // add to all directories and as subdirectory to current
                                val directory = ElfDirectory(name, currentDirectory)
                                allDirectories.add(directory)
                                currentDirectory.directories.putIfAbsent(name, directory)
                            }
                            else -> currentDirectory.files.putIfAbsent(name, ElfFile(name, sizeOrDir.toLong()))
                        }
                    }
                    nextIndex++
                }
            }
            "cd" -> {
                currentDirectory = when (location) {
                    "/" -> rootDirectory
                    ".." -> currentDirectory.parent!!
                    else -> currentDirectory.directories[location]!!
                }
            }
            else -> {
                // do nothing with files because we've already added them
            }
        }
    }
    return allDirectories
}

data class ElfFile(val name: String, val size: Long)

data class ElfDirectory(val name: String, val parent: ElfDirectory?) {
    val files = mutableMapOf<String, ElfFile>()
    val directories = mutableMapOf<String, ElfDirectory>()

    val size: Long by lazy {
        files.values.sumOf { it.size } + directories.values.sumOf { it.size }
    }
}
