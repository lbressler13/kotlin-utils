package xyz.lbres.kotlinutils.longarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Long]: value to assign
 */
fun LongArray.setAllValues(value: Long) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Long]: value to match
 * @return [Int]: number of elements with the given value
 */
fun LongArray.countElement(element: Long) = this.count { it == element }
