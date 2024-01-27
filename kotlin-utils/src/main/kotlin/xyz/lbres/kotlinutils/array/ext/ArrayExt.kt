package xyz.lbres.kotlinutils.array.ext

/**
 * Assign all indices to have the same value
 *
 * @param value T: value to assign
 */
fun <T> Array<T>.setAllValues(value: T) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element T: value to match
 * @return [Int]: number of elements with the given value
 */
fun <T> Array<T>.countElement(element: T) = this.count { it == element }

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (T) -> T: function to generate new values
 */
fun <T> Array<T>.mapInPlace(transform: (T) -> T) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, T) -> T: function to generate new values
 */
fun <T> Array<T>.mapInPlaceIndexed(transform: (Int, T) -> T) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
