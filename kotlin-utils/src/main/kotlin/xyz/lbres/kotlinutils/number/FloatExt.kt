package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.simpleIf
import kotlin.math.abs

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> [Float]
 * @return [Float] the current value, or the default
 */
fun Float.ifZero(getDefaultValue: () -> Float): Float = simpleIf(isZero(), { getDefaultValue() }, { this })

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun Float.isZero(): Boolean = abs(this) == 0f

/**
 * Unary check to determine if value is negative
 *
 * @return [Boolean]: true if value is less than zero, false otherwise
 */
fun Float.isNegative(): Boolean = this < -0f

/**
 * Unary check to determine if value is null or zero
 *
 * @return [Boolean]: true if the value is null or zero, or false otherwise
 */
fun Float?.isNullOrZero(): Boolean = this == null || isZero()
