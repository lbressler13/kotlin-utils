package xyz.lbres.kotlinutils.longarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Long]: value to assign
 */
fun LongArray.setAllValues(value: Long) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Long]: value to match
 * @return [Int]: number of elements with the given value
 */
fun LongArray.countElement(element: Long) = this.count { it == element }

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Long) -> Long: function to generate new values
 */
fun LongArray.mapInPlace(transform: (Long) -> Long) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Long, Long) -> Long: function to generate new values
 */
fun LongArray.mapInPlaceIndexed(transform: (Int, Long) -> Long) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
