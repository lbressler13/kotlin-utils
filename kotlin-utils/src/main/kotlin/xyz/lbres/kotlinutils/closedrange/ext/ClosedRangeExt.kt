package xyz.lbres.kotlinutils.closedrange.ext

/**
 * Functions that can be used by all implementations of ClosedRange, such as IntRange, LongRange, and CharRange
 */

/**
 * If range consists of a single value
 *
 * @return [Boolean]: true if range consists of single value, false otherwise
 */
fun <T : Comparable<T>> ClosedRange<T>.isSingleValue(): Boolean = start == endInclusive

/**
 * If range does not consist of a single value
 *
 * @return [Boolean]: true if range consists of multiple value, false otherwise
 */
fun <T : Comparable<T>> ClosedRange<T>.isNotSingleValue(): Boolean = start != endInclusive
