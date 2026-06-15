package xyz.lbres.kotlinutils.array.booleanarray

/**
 * Determine if all values in array are `true`
 *
 * @return [Boolean]: `true` if all values are set to `true`, `false` otherwise
 */
fun BooleanArray.all() = all { it }

/**
 * Determine if none of the values in array are `true`
 *
 * @return [Boolean]: `true` if no values are set to `true`, `false` otherwise
 */
fun BooleanArray.none() = none { it }

/**
 * Determine if any of the values in array are `true`
 *
 * @return [Boolean]: `true` if any values are set to `true`, `false` otherwise
 */
fun BooleanArray.any() = any { it }
