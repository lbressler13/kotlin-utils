package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.simpleIf
import kotlin.math.abs

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> [Double]
 * @return [Double] the current value, or the default
 */
fun Double.ifZero(getDefaultValue: () -> Double): Double = simpleIf(isZero(), { getDefaultValue() }, { this })

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun Double.isZero(): Boolean = abs(this) == 0.0

/**
 * Unary check to determine if value is negative
 *
 * @return [Boolean]: true if value is less than zero, false otherwise
 */
fun Double.isNegative(): Boolean = this < -0.0

/**
 * Returns true if value is null or zero, or false otherwise
 *
 * @return [Boolean]
 */
fun Double?.isNullOrZero(): Boolean = this == null || isZero()
