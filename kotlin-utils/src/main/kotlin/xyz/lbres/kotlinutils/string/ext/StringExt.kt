package xyz.lbres.kotlinutils.string.ext

import xyz.lbres.kotlinutils.general.tryDefault

/**
 * Substring function which uses end index instead of start index
 *
 * @param index [Int]: end index of substring
 * @return [String] substring of this, starting at 0 and ending at current index
 * @throws IndexOutOfBoundsException if index is greater than size or less than 0
 */
fun String.substringTo(index: Int): String = substring(0, index)

/**
 * Get number of characters matching a specific value
 *
 * @param element [Char]: value to match
 * @return [Int]: number of characters with the given value
 */
fun String.countElement(element: Char) = this.count { it == element }

/**
 * Determine if string can be parsed as Int
 *
 * @return [Boolean]: true if string can be parsed as Int, false otherwise
 */
fun String.isInt(): Boolean {
    return tryDefault(false) {
        toInt()
        true
    }
}
