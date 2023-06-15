package xyz.lbres.kotlinutils.longarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Long]: value to assign
 */
fun LongArray.setAllValues(value: Long) {
    indices.forEach { set(it, value) }
}
