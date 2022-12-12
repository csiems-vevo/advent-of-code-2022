package shared

// Modified version of takeWhile from the Kotlin Standard Library
inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (predicate(item))
            break
    }
    return list
}

fun Iterable<Int>.product(): Int = this.reduce(Int::times)
fun Iterable<Long>.product(): Long = this.reduce(Long::times)
