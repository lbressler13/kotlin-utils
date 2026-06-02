package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.succeeds
import xyz.lbres.kotlinutils.utils.tryOrDefault
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

// TODO split some of these into big int only?

/**
 * Unary check to determine if value is zero
 *
 * @return [Boolean]: true if value is zero, false otherwise
 */
fun BigDecimal.isZero(): Boolean = tryOrDefault(false) { toBigIntegerExact().isZero() }

/**
 * Unary check to determine if value is negative
 *
 * @return [Boolean]: true if value is less than zero, false otherwise
 */
fun BigDecimal.isNegative(): Boolean = this < BigDecimal.ZERO

/**
 * Determine if value is a whole number
 *
 * @return `true` if number is a whole number, `false` otherwise
 */
fun BigDecimal.isWholeNumber(): Boolean = succeeds { toBigIntegerExact() }

/**
 * Round to the nearest whole number using the provided rounding mode
 *
 * @param roundingMode [RoundingMode]: mode to use when rounding. Defaults to [RoundingMode.HALF_UP]
 * @return [BigInteger]: closest whole number by the specified mode
 */
fun BigDecimal.roundToBigInteger(roundingMode: RoundingMode = RoundingMode.HALF_UP): BigInteger {
    return setScale(0, roundingMode).toBigInteger()
}
