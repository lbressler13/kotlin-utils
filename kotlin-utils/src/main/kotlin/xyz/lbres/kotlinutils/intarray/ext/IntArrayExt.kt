package xyz.lbres.kotlinutils.intarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Int]: value to assign
 */
fun IntArray.setAllValues(value: Int) {
    indices.forEach { set(it, value) }
}
