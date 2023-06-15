package xyz.lbres.kotlinutils.chararray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Char]: value to assign
 */
fun CharArray.setAllValues(value: Char) {
    indices.forEach { set(it, value) }
}
