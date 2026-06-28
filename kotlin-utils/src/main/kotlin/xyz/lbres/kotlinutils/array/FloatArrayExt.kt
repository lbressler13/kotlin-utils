package xyz.lbres.kotlinutils.array

import xyz.lbres.kotlinutils.number.isZero
import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Assign all indices to have the same value
 *
 * @param value [Float]: value to assign
 */
fun FloatArray.setAllValues(value: Float) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Float]: value to match
 * @return [Int]: number of elements with the given value
 */
fun FloatArray.countElement(element: Float) = this.count { it == element }

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Float) -> Float: function to generate new values
 */
fun FloatArray.mapInPlace(transform: (Float) -> Float) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Float) -> Float: function to generate new values
 */
fun FloatArray.mapInPlaceIndexed(transform: (Int, Float) -> Float) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}

/**
 * Filter a float array to contain only elements that do not equal zero.
 *
 * @return [List]<Float>: list containing the same values as this array, except any elements with value 0.
 */
fun FloatArray.filterNotZero(): List<Float> = filterNot { it.isZero() }

/**
 * Add all values in array.
 *
 * @return [Float]: sum of numbers in array
 */
fun FloatArray.sum(): Float = fold(0f, Float::plus)

/**
 * Multiply all values in array
 *
 * @return [Float]: product of numbers in array, or 0 if array is empty
 */
fun FloatArray.product(): Float = simpleIf(isEmpty(), 0f, fold(1f, Float::times))
