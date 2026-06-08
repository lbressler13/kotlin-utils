package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.simpleIf
import java.math.BigInteger

/**
 * Unary check to determine if value is negative
 *
 * @return [Boolean]: true if value is less than zero, false otherwise
 */
fun BigInteger.isNegative(): Boolean = this < BigInteger.ZERO

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun BigInteger.isZero(): Boolean = equals(BigInteger.ZERO)

/**
 * Returns this number if not zero, or the result of calling [getDefaultValue] if it is.
 *
 * @param getDefaultValue () -> BigInteger
 * @return [BigInteger] the current value, or the default
 */
fun BigInteger.ifZero(getDefaultValue: () -> BigInteger): BigInteger = simpleIf(isZero(), { getDefaultValue() }, { this })

/**
 * Returns true if value is null or zero, or false otherwise
 *
 * @return [Boolean]
 */
fun BigInteger?.isNullOrZero(): Boolean = this == null || isZero()
