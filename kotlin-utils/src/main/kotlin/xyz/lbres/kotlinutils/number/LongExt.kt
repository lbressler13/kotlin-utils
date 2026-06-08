package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> [Long]
 * @return [Long] the current value, or the default
 */
fun Long.ifZero(getDefaultValue: () -> Long): Long = simpleIf(isZero(), { getDefaultValue() }, { this })

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun Long.isZero(): Boolean = equals(0L)

/**
 * Unary check to determine if value is negative
 *
 * @return [Boolean]: true if value is less than zero, false otherwise
 */
fun Long.isNegative(): Boolean = this < 0L

/**
 * Returns true if value is null or zero, or false otherwise
 *
 * @return [Boolean]
 */
fun Long?.isNullOrZero(): Boolean = this == null || isZero()
