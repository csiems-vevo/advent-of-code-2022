fun findPacketMarker(input: String): Int {
    return findMarker(input, 4)
}

fun findMessageMarker(input: String): Int {
    return findMarker(input, 14)
}

private fun findMarker(input: String, size: Int): Int {
    return input
        .windowed(size, partialWindows = false)
        .indexOfFirst { it.allUnique() } + size
}

private fun String.allUnique(): Boolean {
    val set = HashSet<Char>()
    return all { set.add(it) }
}
