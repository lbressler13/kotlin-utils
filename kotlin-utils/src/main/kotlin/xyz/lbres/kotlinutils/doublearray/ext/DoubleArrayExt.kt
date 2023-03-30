package xyz.lbres.kotlinutils.doublearray.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [Double]: value to assign
 */
fun DoubleArray.setAllValues(value: Double) {
    indices.forEach { set(it, value) }
}
