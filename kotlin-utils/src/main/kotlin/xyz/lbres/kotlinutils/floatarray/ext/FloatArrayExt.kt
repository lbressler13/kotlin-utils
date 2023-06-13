package xyz.lbres.kotlinutils.floatarray.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [Float]: value to assign
 */
fun FloatArray.setAllValues(value: Float) {
    indices.forEach { set(it, value) }
}
