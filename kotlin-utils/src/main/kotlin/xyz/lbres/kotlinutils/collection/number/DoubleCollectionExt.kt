package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.number.isZero
import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Filter a double collection to contain only elements that do not equal zero.
 *
 * @return [List]<Double>: list containing the same values as this collection, except any elements with value 0.
 */
fun Collection<Double>.filterNotZero(): List<Double> = filterNot { it.isZero() }

/**
 * Add all values in collection.
 *
 * @return [Double]: sum of numbers in collection
 */
fun Collection<Double>.sum(): Double = fold(0.0, Double::plus)

/**
 * Multiply all values in collection
 *
 * @return [Double]: product of numbers in collection, or 0 if collection is empty
 */
fun Collection<Double>.product(): Double = simpleIf(isEmpty(), 0.0, fold(1.0, Double::times))
