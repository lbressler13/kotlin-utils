package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> [Byte]
 * @return [Byte] the current value, or the default
 */
fun Byte.ifZero(getDefaultValue: () -> Byte): Byte = simpleIf(isZero(), { getDefaultValue() }, { this })

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun Byte.isZero(): Boolean = equals(0.toByte())

/**
 * Unary check to determine if value is negative
 *
 * @return [Boolean]: true if value is less than zero, false otherwise
 */
fun Byte.isNegative(): Boolean = this < 0.toByte()

/**
 * Returns true if value is null or zero, or false otherwise
 *
 * @return [Boolean]
 */
fun Byte?.isNullOrZero(): Boolean = this == null || isZero()
