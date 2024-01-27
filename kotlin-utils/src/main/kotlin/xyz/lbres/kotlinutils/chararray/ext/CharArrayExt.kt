package xyz.lbres.kotlinutils.chararray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Char]: value to assign
 */
fun CharArray.setAllValues(value: Char) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Char]: value to match
 * @return [Int]: number of elements with the given value
 */
fun CharArray.countElement(element: Char) = this.count { it == element }

/**
 * Replace all values in the array based on a provided transform function, without generating a new array
 *
 * @param transform (Char) -> Char: function to generate new values
 */
fun CharArray.mapInPlace(transform: (Char) -> Char) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array based on a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Char) -> Char: function to generate new values
 */
fun CharArray.mapInPlaceIndexed(transform: (Int, Char) -> Char) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
