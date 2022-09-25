package xyz.lbres.kotlinutils.closedrange.longrange.ext

/**
 * Get number of elements in range, inclusive of start and end
 *
 * @return [Long]: size of range
 */
fun LongRange.size(): Long = last - first + 1

/**
 * Mimic getting an indexed value in a List by getting nth value in range
 *
 * @param index [Long]: index of value to retrieve
 * @return [Long]: the nth value in the range, where n is the index
 * @throws [IndexOutOfBoundsException] if the specified index is less than zero, or greater than or equal to the size of the range
 */
fun LongRange.get(index: Long): Long {
    if (index !in 0 until size()) {
        throw IndexOutOfBoundsException()
    }

    return first + index
}
