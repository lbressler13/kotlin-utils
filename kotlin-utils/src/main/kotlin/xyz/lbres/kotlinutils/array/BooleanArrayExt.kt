package xyz.lbres.kotlinutils.array

/**
 * Assign all indices to have the same value
 *
 * @param value [Boolean]: value to assign
 */
fun BooleanArray.setAllValues(value: Boolean) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Boolean]: value to match
 * @return [Int]: number of elements with the given value
 */
fun BooleanArray.countElement(element: Boolean) = this.count { it == element }

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Boolean) -> Boolean: function to generate new values
 */
fun BooleanArray.mapInPlace(transform: (Boolean) -> Boolean) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Boolean) -> Boolean: function to generate new values
 */
fun BooleanArray.mapInPlaceIndexed(transform: (Int, Boolean) -> Boolean) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
