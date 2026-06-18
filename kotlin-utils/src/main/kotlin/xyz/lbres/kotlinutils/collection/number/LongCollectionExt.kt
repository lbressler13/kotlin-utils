package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.number.isZero
import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Filter a long collection to contain only elements that do not equal zero.
 *
 * @return [List]<Long>: list containing the same values as this collection, except any elements with value 0.
 */
fun Collection<Long>.filterNotZero(): List<Long> = filterNot { it.isZero() }

/**
 * Add all values in collection.
 *
 * @return [Long]: sum of numbers in collection
 */
fun Collection<Long>.sum(): Long = fold(0, Long::plus)

/**
 * Multiply all values in collection
 *
 * @return [Long]: product of numbers in collection, or 0 if collection is empty
 */
fun Collection<Long>.product(): Long = simpleIf(isEmpty(), 0, fold(1, Long::times))
