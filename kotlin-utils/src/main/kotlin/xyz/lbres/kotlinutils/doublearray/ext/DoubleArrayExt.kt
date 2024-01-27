package xyz.lbres.kotlinutils.doublearray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Double]: value to assign
 */
fun DoubleArray.setAllValues(value: Double) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Double]: value to match
 * @return [Int]: number of elements with the given value
 */
fun DoubleArray.countElement(element: Double) = this.count { it == element }

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Double) -> Double: function to generate new values
 */
fun DoubleArray.mapInPlace(transform: (Double) -> Double) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Double) -> Double: function to generate new values
 */
fun DoubleArray.mapInPlaceIndexed(transform: (Int, Double) -> Double) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
