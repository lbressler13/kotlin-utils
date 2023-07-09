package xyz.lbres.kotlinutils.booleanarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Boolean]: value to assign
 */
fun BooleanArray.setAllValues(value: Boolean) {
    indices.forEach { set(it, value) }
}

/**
 * Determine if all values in array are `true`
 *
 * @return [Boolean]: `true` if all values are set to `true`, `false` otherwise
 */
fun BooleanArray.all() = all { it }
