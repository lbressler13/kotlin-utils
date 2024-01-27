package xyz.lbres.kotlinutils.floatarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Float]: value to assign
 */
fun FloatArray.setAllValues(value: Float) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Float]: value to match
 * @return [Int]: number of elements with the given value
 */
fun FloatArray.countElement(element: Float) = this.count { it == element }

/**
 * Replace all values in the array based on a provided transform function, without generating a new array
 *
 * @param transform (Float) -> Float: function to generate new values
 */
fun FloatArray.mapInPlace(transform: (Float) -> Float) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array based on a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Float) -> Float: function to generate new values
 */
fun FloatArray.mapInPlaceIndexed(transform: (Int, Float) -> Float) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
