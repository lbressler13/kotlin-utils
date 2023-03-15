package xyz.lbres.kotlinutils.chararray.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [Char]: value to assign
 */
fun CharArray.setAllValues(value: Char) {
    indices.forEach { set(it, value) }
}
