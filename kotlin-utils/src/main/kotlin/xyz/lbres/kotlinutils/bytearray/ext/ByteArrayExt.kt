package xyz.lbres.kotlinutils.bytearray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Byte]: value to assign
 */
fun ByteArray.setAllValues(value: Byte) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Byte]: value to match
 * @return [Int]: number of elements with the given value
 */
fun ByteArray.countElement(element: Byte) = this.count { it == element }
