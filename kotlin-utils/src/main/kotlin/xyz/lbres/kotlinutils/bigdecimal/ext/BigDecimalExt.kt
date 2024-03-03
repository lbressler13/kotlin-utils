package xyz.lbres.kotlinutils.bigdecimal.ext

import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.general.tryOrDefault
import java.math.BigDecimal

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
