package xyz.lbres.kotlinutils.number

import xyz.lbres.kotlinutils.utils.succeeds
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
 * Unary check to determine if value is a whole number
 *
 * @return [Boolean]: true if value is a whole number, false otherwise
 */
fun BigDecimal.isWholeNumber(): Boolean = succeeds { toBigIntegerExact() }

/**
 * Unary check to determine if value is null or zero
 *
 * @return [Boolean]: true if the value is null or zero, or false otherwise
 */
fun BigDecimal?.isNullOrZero(): Boolean = this == null || isZero()
