package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.number.isZero
import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Filter a float collection to contain only elements that do not equal zero.
 *
 * @return [Collection]<Float>: collection containing the same values as this collection, except any elements with value 0.
 */
fun Collection<Float>.filterNotZero(): Collection<Float> = filterNot { it.isZero() }

/**
 * Add all values in collection.
 *
 * @return [Float]: sum of numbers in collection
 */
fun Collection<Float>.sum(): Float = fold(0f, Float::plus)

/**
 * Multiply all values in collection
 *
 * @return [Float]: product of numbers in collection, or 0 if collection is empty
 */
fun Collection<Float>.product(): Float = simpleIf(isEmpty(), 0f, fold(1f, Float::times))
