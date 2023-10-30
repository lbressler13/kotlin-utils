package xyz.lbres.kotlinutils.collection.char.ext

import xyz.lbres.kotlinutils.char.ext.isZero

/**
 * Filter a char collection to contain only elements that do not equal zero.
 *
 * @return [Collection]<Char>: collection containing the same values as this collection, except any elements with value 0.
 */
fun Collection<Char>.filterNotZero(): Collection<Char> = filterNot { it.isZero() }

/**
 * Add all values in collection.
 *
 * @return [Char]: sum of numbers in collection
 */
fun Collection<Char>.sum(): Char = fold(Char(0)) { acc, char -> acc + char.code }

/**
 * Multiply all values in collection
 *
 * @return [Char]: product of numbers in collection, or 0 if collection is empty
 */
fun Collection<Char>.product(): Char {
    if (isEmpty()) {
        return Char(0)
    }

    return fold(1) { acc, char -> acc * char.code }.toChar()
}
