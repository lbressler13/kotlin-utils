package xyz.lbres.kotlinutils.collection.boolean.ext

/**
 * Determine if all values in collection are `true`
 *
 * @return [Boolean]: `true` if all values are set to `true`, `false` otherwise
 */
fun Collection<Boolean>.all() = all { it }

/**
 * Determine if none of the values in array are `true`
 *
 * @return [Boolean]: `true` if no values are set to `true`, `false` otherwise
 */
fun Collection<Boolean>.none() = none { it }

/**
 * Determine if any of the values in array are `true`
 *
 * @return [Boolean]: `true` if any values are set to `true`, `false` otherwise
 */
fun Collection<Boolean>.any() = any { it }
