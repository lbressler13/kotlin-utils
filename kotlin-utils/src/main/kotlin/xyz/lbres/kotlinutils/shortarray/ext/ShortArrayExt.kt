package xyz.lbres.kotlinutils.shortarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Short]: value to assign
 */
fun ShortArray.setAllValues(value: Short) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Short]: value to match
 * @return [Int]: number of elements with the given value
 */
fun ShortArray.countElement(element: Short) = this.count { it == element }
