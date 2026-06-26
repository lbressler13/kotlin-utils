package xyz.lbres.kotlinutils.array

import xyz.lbres.kotlinutils.number.isZero
import xyz.lbres.kotlinutils.utils.simpleIf

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

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Int) -> Int: function to generate new values
 */
fun IntArray.mapInPlace(transform: (Int) -> Int) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Int) -> Int: function to generate new values
 */
fun IntArray.mapInPlaceIndexed(transform: (Int, Int) -> Int) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}

/**
 * Filter an int array to contain only elements that do not equal zero.
 *
 * @return [List]<Int>: list containing the same values as this array, except any elements with value 0.
 */
fun IntArray.filterNotZero(): List<Int> = filterNot { it.isZero() }

/**
 * Add all values in array.
 *
 * @return [Int]: sum of numbers in array
 */
fun IntArray.sum(): Int = fold(0, Int::plus)

/**
 * Multiply all values in array
 *
 * @return [Int]: product of numbers in array, or 0 if array is empty
 */
fun IntArray.product(): Int = simpleIf(isEmpty(), 0, fold(1, Int::times))
