package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> [Char]
 * @return [Char] the current value, or the default
 */
fun Char.ifZero(getDefaultValue: () -> Char): Char = simpleIf(isZero(), { getDefaultValue() }, { this })

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun Char.isZero(): Boolean = code.isZero()

/**
 * Unary check to determine if value is null or zero
 *
 * @return [Boolean]: true if the value is null or zero, or false otherwise
 */
fun Char?.isNullOrZero(): Boolean = this == null || isZero()
