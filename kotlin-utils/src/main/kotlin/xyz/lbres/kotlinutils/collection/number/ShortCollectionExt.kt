package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.number.isZero

/**
 * Filter a short collection to contain only elements that do not equal zero.
 *
 * @return [List]<Short>: list containing the same values as this collection, except any elements with value 0.
 */
fun Collection<Short>.filterNotZero(): List<Short> = filterNot { it.isZero() }

/**
 * Add all values in collection.
 *
 * @return [Short]: sum of numbers in collection
 */
fun Collection<Short>.sum(): Short = fold(0) { acc, sh -> acc + sh }.toShort()

/**
 * Multiply all values in collection
 *
 * @return [Short]: product of numbers in collection, or 0 if collection is empty
 */
fun Collection<Short>.product(): Short {
    return if (isEmpty()) {
        0
    } else {
        fold(1) { acc, sh -> acc * sh }.toShort()
    }
}
