package xyz.lbres.kotlinutils.number.bigdecimal

import xyz.lbres.kotlinutils.internal.constants.basePackage
import xyz.lbres.kotlinutils.utils.succeeds
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * Unary check to determine if value is a whole number
 *
 * @return [Boolean]: true if value is a whole number, false otherwise
 */
@Deprecated("Relocated in v2.1.0", ReplaceWith("isWholeNumber", "$basePackage.number.isWholeNumber"), DeprecationLevel.WARNING)
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
