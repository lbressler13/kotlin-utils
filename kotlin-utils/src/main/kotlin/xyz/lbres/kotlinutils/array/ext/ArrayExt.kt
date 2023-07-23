package xyz.lbres.kotlinutils.array.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [T]: value to assign
 */
fun <T> Array<T>.setAllValues(value: T) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [T]: value to match
 * @return [Int]: number of elements with the given value
 */
fun <T> Array<T>.countElement(element: T) = this.count { it == element }
