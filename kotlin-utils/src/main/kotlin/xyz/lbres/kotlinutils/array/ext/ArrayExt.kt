package xyz.lbres.kotlinutils.array.ext

/**
 * Assign all indices to have the same value
 *
 * @param value [T]: value to assign
 */
fun <T> Array<T>.setAllValues(value: T) {
    indices.forEach { set(it, value) }
}
