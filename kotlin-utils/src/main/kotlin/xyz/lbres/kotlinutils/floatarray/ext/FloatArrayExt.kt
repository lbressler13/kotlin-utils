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
