package xyz.lbres.kotlinutils.intarray.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [Int]: value to assign
 */
fun IntArray.setAllValues(value: Int) {
    indices.forEach { set(it, value) }
}
