package xyz.lbres.kotlinutils.collection.boolean.ext

/**
 * Determine if all values in collection are `true`
 *
 * @return [Boolean]: `true` if all values are set to `true`, `false` otherwise
 */
fun Collection<Boolean>.all() = all { it }
