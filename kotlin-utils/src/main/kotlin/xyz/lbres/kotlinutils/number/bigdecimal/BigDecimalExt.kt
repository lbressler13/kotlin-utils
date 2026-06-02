package xyz.lbres.kotlinutils.number.bigdecimal

import xyz.lbres.kotlinutils.utils.succeeds
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

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
