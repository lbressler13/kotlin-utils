package xyz.lbres.kotlinutils.array

import xyz.lbres.kotlinutils.number.isZero
import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Assign all indices to have the same value
 *
 * @param value [Double]: value to assign
 */
fun DoubleArray.setAllValues(value: Double) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Double]: value to match
 * @return [Int]: number of elements with the given value
 */
fun DoubleArray.countElement(element: Double) = this.count { it == element }

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Double) -> Double: function to generate new values
 */
fun DoubleArray.mapInPlace(transform: (Double) -> Double) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Double) -> Double: function to generate new values
 */
fun DoubleArray.mapInPlaceIndexed(transform: (Int, Double) -> Double) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}

/**
 * Filter a double array to contain only elements that do not equal zero.
 *
 * @return [List]<Double>: list containing the same values as this array, except any elements with value 0.
 */
fun DoubleArray.filterNotZero(): List<Double> = filterNot { it.isZero() }

/**
 * Add all values in array.
 *
 * @return [Double]: sum of numbers in array
 */
fun DoubleArray.sum(): Double = fold(0.0, Double::plus)

/**
 * Multiply all values in array
 *
 * @return [Double]: product of numbers in array, or 0 if array is empty
 */
fun DoubleArray.product(): Double = simpleIf(isEmpty(), 0.0, fold(1.0, Double::times))
