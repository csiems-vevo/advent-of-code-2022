fun findPacketMarker(input: String): Int {
    return findMarker(input, 4)
}

fun findMessageMarker(input: String): Int {
    return findMarker(input, 14)
}

private fun findMarker(input: String, offset: Int): Int {
    val window = input
        .windowed(offset, partialWindows = false)
        .first { window -> window.all { window.count { char -> it == char } == 1 } }
    return input.indexOf(window) + offset
}
