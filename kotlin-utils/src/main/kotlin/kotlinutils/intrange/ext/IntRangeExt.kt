package kotlinutils.intrange.ext

// TODO generalize to all ranges

/**
 * Get number of elements in range, inclusive of start and end
 *
 * @return [Int]: size of range
 */
fun IntRange.size(): Int = last - first + 1

/**
 * If range consists of a single value
 *
 * @return [Boolean]: true if range consists of single value, false otherwise
 */
fun IntRange.isSingleValue(): Boolean = first == last

/**
 * If range does not consist of a single value
 *
 * @return [Boolean]: true if range consists of multiple value, false otherwise
 */
fun IntRange.isNotSingleValue(): Boolean = first != last

/**
 * Mimic getting an indexed value in a List by getting nth value in range
 *
 * @param index [Int]: index of value to retrieve
 * @return [Int]: the nth value in the range, where n is the index
 * @throws [IndexOutOfBoundsException] if the specified index is less than zero, or greater than or equal to the size of the range
 */
fun IntRange.get(index: Int): Int {
    if (index !in 0 until size()) {
        throw IndexOutOfBoundsException()
    }

    return first + index
}
