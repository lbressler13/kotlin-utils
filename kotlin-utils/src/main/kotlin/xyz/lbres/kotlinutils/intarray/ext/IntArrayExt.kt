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
