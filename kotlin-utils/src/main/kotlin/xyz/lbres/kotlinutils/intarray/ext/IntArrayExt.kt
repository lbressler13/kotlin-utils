package xyz.lbres.kotlinutils.intarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Int]: value to assign
 */
fun IntArray.setAllValues(value: Int) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Int]: value to match
 * @return [Int]: number of elements with the given value
 */
fun IntArray.countElement(element: Int) = this.count { it == element }

/**
 * Replace all values in the array based on a provided transform function, without generating a new array
 *
 * @param transform (Int) -> Int: function to generate new values
 */
fun IntArray.mapInPlace(transform: (Int) -> Int) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array based on a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Int) -> Int: function to generate new values
 */
fun IntArray.mapInPlaceIndexed(transform: (Int, Int) -> Int) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
