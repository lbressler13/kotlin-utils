package xyz.lbres.kotlinutils.array

import xyz.lbres.kotlinutils.number.isZero
import xyz.lbres.kotlinutils.utils.simpleIf

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

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Long) -> Long: function to generate new values
 */
fun LongArray.mapInPlace(transform: (Long) -> Long) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Long) -> Long: function to generate new values
 */
fun LongArray.mapInPlaceIndexed(transform: (Int, Long) -> Long) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}

/**
 * Filter a long array to contain only elements that do not equal zero.
 *
 * @return [List]<Long>: list containing the same values as this array, except any elements with value 0.
 */
fun LongArray.filterNotZero(): List<Long> = filterNot { it.isZero() }

/**
 * Add all values in array.
 *
 * @return [Long]: sum of numbers in array
 */
fun LongArray.sum(): Long = fold(0, Long::plus)

/**
 * Multiply all values in array
 *
 * @return [Long]: product of numbers in array, or 0 if array is empty
 */
fun LongArray.product(): Long = simpleIf(isEmpty(), 0, fold(1, Long::times))
