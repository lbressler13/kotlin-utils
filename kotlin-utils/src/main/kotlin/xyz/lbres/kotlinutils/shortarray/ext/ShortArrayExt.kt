package xyz.lbres.kotlinutils.shortarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Short]: value to assign
 */
fun ShortArray.setAllValues(value: Short) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Short]: value to match
 * @return [Int]: number of elements with the given value
 */
fun ShortArray.countElement(element: Short) = this.count { it == element }

/**
 * Replace all values in the array based on a provided transform function, without generating a new array
 *
 * @param transform (Short) -> Short: function to generate new values
 */
fun ShortArray.mapInPlace(transform: (Short) -> Short) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array based on a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Short, Short) -> Short: function to generate new values
 */
fun ShortArray.mapInPlaceIndexed(transform: (Int, Short) -> Short) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
