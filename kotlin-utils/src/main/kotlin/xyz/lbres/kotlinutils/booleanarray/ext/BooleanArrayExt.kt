package xyz.lbres.kotlinutils.booleanarray.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [Boolean]: value to assign
 */
fun BooleanArray.setAllValues(value: Boolean) {
    indices.forEach { set(it, value) }
}
