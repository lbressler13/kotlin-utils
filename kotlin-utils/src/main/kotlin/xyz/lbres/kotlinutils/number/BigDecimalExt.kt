package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.tryOrDefault
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

/**
 * Returns true if value is null or zero, or false otherwise
 *
 * @return [Boolean]
 */
fun BigDecimal?.isNullOrZero(): Boolean = this == null || isZero()
