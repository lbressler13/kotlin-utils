package xyz.lbres.kotlinutils.collection.int.ext

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.int.ext.isZero

/**
 * Filter an integer collection to contain only elements that do not equal zero.
 *
 * @return [Collection]<Int>: collection containing the same values as this collection, except any elements with value 0.
 */
fun Collection<Int>.filterNotZero(): Collection<Int> = filterNot { it.isZero() }

/**
 * Add all values in collection.
 *
 * @return [Int]: sum of numbers in collection
 */
fun Collection<Int>.sum(): Int = fold(0, Int::plus)

/**
 * Multiply all values in collection
 *
 * @return [Int]: product of numbers in collection, or 0 if collection is empty
 */
fun Collection<Int>.product(): Int = simpleIf(isEmpty(), 0, fold(1, Int::times))
