package xyz.lbres.kotlinutils.floatarray.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [Float]: value to assign
 */
fun FloatArray.setAllValues(value: Float) {
    indices.forEach { set(it, value) }
}
