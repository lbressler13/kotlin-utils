package xyz.lbres.kotlinutils.doublearray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Double]: value to assign
 */
fun DoubleArray.setAllValues(value: Double) {
    indices.forEach { set(it, value) }
}
