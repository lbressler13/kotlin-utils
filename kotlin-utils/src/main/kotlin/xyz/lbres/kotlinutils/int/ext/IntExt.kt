package xyz.lbres.kotlinutils.int.ext

import xyz.lbres.kotlinutils.general.simpleIf

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> [Int]
 * @return [Int] the current value, or the default
 */
fun Int.ifZero(getDefaultValue: () -> Int): Int = simpleIf(isZero(), { getDefaultValue() }, { this })

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun Int.isZero(): Boolean = equals(0)

/**
 * Unary check to determine if value is negative
 *
 * @return [Boolean]: true if value is less than zero, false otherwise
 */
fun Int.isNegative(): Boolean = this < 0
