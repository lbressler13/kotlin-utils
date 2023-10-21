package xyz.lbres.kotlinutils.collection.long.ext

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.long.ext.isZero

/**
 * Filter a long collection to contain only elements that do not equal zero.
 *
 * @return [Collection]<Long>: collection containing the same values as [this], except any elements with value 0.
 */
fun Collection<Long>.filterNotZero(): Collection<Long> = filterNot { it.isZero() }

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
