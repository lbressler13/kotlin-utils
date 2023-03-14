package xyz.lbres.kotlinutils.shortarray.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [Short]: value to assign
 */
fun ShortArray.setAllValues(value: Short) {
    indices.forEach { set(it, value) }
}
