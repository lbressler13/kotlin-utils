package xyz.lbres.kotlinutils.booleanarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Boolean]: value to assign
 */
fun BooleanArray.setAllValues(value: Boolean) {
    indices.forEach { set(it, value) }
}
