package xyz.lbres.kotlinutils.collection.number

import xyz.lbres.kotlinutils.number.isZero

/**
 * Filter a byte collection to contain only elements that do not equal zero.
 *
 * @return [Collection]<Byte>: collection containing the same values as this collection, except any elements with value 0.
 */
fun Collection<Byte>.filterNotZero(): Collection<Byte> = filterNot { it.isZero() }

/**
 * Add all values in collection.
 *
 * @return [Byte]: sum of numbers in collection
 */
fun Collection<Byte>.sum(): Byte = fold(0) { acc, byte -> acc + byte }.toByte()

/**
 * Multiply all values in collection
 *
 * @return [Byte]: product of numbers in collection, or 0 if collection is empty
 */
fun Collection<Byte>.product(): Byte {
    return if (isEmpty()) {
        0
    } else {
        fold(1) { acc, byte -> acc * byte }.toByte()
    }
}
