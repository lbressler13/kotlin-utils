package xyz.lbres.kotlinutils.shortarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Short]: value to assign
 */
fun ShortArray.setAllValues(value: Short) {
    indices.forEach { set(it, value) }
}
