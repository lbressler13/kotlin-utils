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
