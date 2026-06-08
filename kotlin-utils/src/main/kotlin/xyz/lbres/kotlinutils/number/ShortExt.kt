package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.simpleIf

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> [Short]
 * @return [Short] the current value, or the default
 */
fun Short.ifZero(getDefaultValue: () -> Short): Short = simpleIf(isZero(), { getDefaultValue() }, { this })

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun Short.isZero(): Boolean = equals(0.toShort())

/**
 * Unary check to determine if value is negative
 *
 * @return [Boolean]: true if value is less than zero, false otherwise
 */
fun Short.isNegative(): Boolean = this < 0.toShort()

/**
 * Returns true if value is null or zero, or false otherwise
 *
 * @return [Boolean]
 */
fun Short?.isNullOrZero(): Boolean = this == null || isZero()
