package xyz.lbres.kotlinutils.array

import xyz.lbres.kotlinutils.number.isZero

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
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Byte) -> Byte: function to generate new values
 */
fun ByteArray.mapInPlace(transform: (Byte) -> Byte) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Byte) -> Byte: function to generate new values
 */
fun ByteArray.mapInPlaceIndexed(transform: (Int, Byte) -> Byte) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}

/**
 * Filter a byte array to contain only elements that do not equal zero.
 *
 * @return [List]<Byte>: list containing the same values as this array, except any elements with value 0.
 */
fun ByteArray.filterNotZero(): List<Byte> = filterNot { it.isZero() }

/**
 * Add all values in array.
 *
 * @return [Byte]: sum of numbers in array
 */
fun ByteArray.sum(): Byte = fold(0) { acc, byte -> acc + byte }.toByte()

/**
 * Multiply all values in array
 *
 * @return [Byte]: product of numbers in array, or 0 if array is empty
 */
fun ByteArray.product(): Byte {
    return if (isEmpty()) {
        0
    } else {
        fold(1) { acc, byte -> acc * byte }.toByte()
    }
}
