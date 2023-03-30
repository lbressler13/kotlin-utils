package xyz.lbres.kotlinutils.longarray.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [Long]: value to assign
 */
fun LongArray.setAllValues(value: Long) {
    indices.forEach { set(it, value) }
}
