package xyz.lbres.kotlinutils.array.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [T]: value to assign
 */
fun <T> Array<T>.setAllValues(value: T) {
    indices.forEach { set(it, value) }
}
