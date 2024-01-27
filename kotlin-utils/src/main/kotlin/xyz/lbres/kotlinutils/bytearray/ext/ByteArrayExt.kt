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

/**
 * Replace all values in the array based on a provided transform function, without generating a new array
 *
 * @param transform (Byte) -> Byte: function to generate new values
 */
fun ByteArray.mapInPlace(transform: (Byte) -> Byte) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array based on a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Byte) -> Byte: function to generate new values
 */
fun ByteArray.mapInPlaceIndexed(transform: (Int, Byte) -> Byte) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}
