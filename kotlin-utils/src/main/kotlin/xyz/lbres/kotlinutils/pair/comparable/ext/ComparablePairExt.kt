package xyz.lbres.kotlinutils.pair.comparable.ext

import xyz.lbres.kotlinutils.general.simpleIf

/**
 * Get larger of the values in the pair
 *
 * @return T: maximum of first and second values
 */
fun <T : Comparable<T>> Pair<T, T>.max(): T = simpleIf(first < second, second, first)

/**
 * Get smaller of the values in the pair
 *
 * @return T: minimum of first and second values
 */
fun <T : Comparable<T>> Pair<T, T>.min(): T = simpleIf(first < second, first, second)
