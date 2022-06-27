package kotlinutils.biginteger.ext

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
