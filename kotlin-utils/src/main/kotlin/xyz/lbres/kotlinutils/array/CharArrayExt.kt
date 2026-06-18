package xyz.lbres.kotlinutils.array

import xyz.lbres.kotlinutils.number.isZero

/**
 * Assign all indices to have the same value
 *
 * @param value [Char]: value to assign
 */
fun CharArray.setAllValues(value: Char) {
    indices.forEach { set(it, value) }
}

/**
 * Get number of elements matching a specific value
 *
 * @param element [Char]: value to match
 * @return [Int]: number of elements with the given value
 */
fun CharArray.countElement(element: Char) = this.count { it == element }

/**
 * Replace all values in the array using a provided transform function, without generating a new array
 *
 * @param transform (Char) -> Char: function to generate new values
 */
fun CharArray.mapInPlace(transform: (Char) -> Char) {
    forEachIndexed { index, value -> set(index, transform(value)) }
}

/**
 * Replace all values in the array using a provided transform function that uses both the value and the index,
 * without generating a new array
 *
 * @param transform (Int, Char) -> Char: function to generate new values
 */
fun CharArray.mapInPlaceIndexed(transform: (Int, Char) -> Char) {
    forEachIndexed { index, value -> set(index, transform(index, value)) }
}

/**
 * Filter a char array to contain only elements that do not equal zero.
 *
 * @return [List]<Char>: list containing the same values as this array, except any elements with value 0.
 */
fun CharArray.filterNotZero(): List<Char> = filterNot { it.isZero() }

/**
 * Add all values in array.
 *
 * @return [Char]: sum of numbers in array
 */
fun CharArray.sum(): Char = fold(Char(0)) { acc, char -> acc + char.code }

/**
 * Multiply all values in array
 *
 * @return [Char]: product of numbers in array, or 0 if array is empty
 */
fun CharArray.product(): Char {
    if (isEmpty()) {
        return Char(0)
    }

    return fold(1) { acc, char -> acc * char.code }.toChar()
}
