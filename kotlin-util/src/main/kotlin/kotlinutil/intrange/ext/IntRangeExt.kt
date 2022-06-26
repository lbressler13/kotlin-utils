package kotlinutil.intrange.ext

// TODO possibly generalize to all ranges

fun IntRange.size(): Int {
    return last - first + 1
}

fun IntRange.isSingleValue(): Boolean {
    return first == last
}

fun IntRange.isNotSingleValue(): Boolean {
    return first != last
}

fun IntRange.get(index: Int): Int {
    if (index >= size()) {
        throw IndexOutOfBoundsException()
    }

    return first + index
}
