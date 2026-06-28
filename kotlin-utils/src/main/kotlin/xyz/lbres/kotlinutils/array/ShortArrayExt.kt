package xyz.lbres.kotlinutils.array

import xyz.lbres.kotlinutils.number.isZero

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

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Short) -> Short: function to generate new values
 */
fun ShortArray.mapInPlace(transform: (Short) -> Short) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Short) -> Short: function to generate new values
 */
fun ShortArray.mapInPlaceIndexed(transform: (Int, Short) -> Short) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}

/**
 * Filter a short array to contain only elements that do not equal zero.
 *
 * @return [List]<Short>: list containing the same values as this array, except any elements with value 0.
 */
fun ShortArray.filterNotZero(): List<Short> = filterNot { it.isZero() }

/**
 * Add all values in array.
 *
 * @return [Short]: sum of numbers in array
 */
fun ShortArray.sum(): Short = fold(0) { acc, sh -> acc + sh }.toShort()

/**
 * Multiply all values in array
 *
 * @return [Short]: product of numbers in array, or 0 if array is empty
 */
fun ShortArray.product(): Short {
    return if (isEmpty()) {
        0
    } else {
        fold(1) { acc, sh -> acc * sh }.toShort()
    }
}
