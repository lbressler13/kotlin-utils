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
