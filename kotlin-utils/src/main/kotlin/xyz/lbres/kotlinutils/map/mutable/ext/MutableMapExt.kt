package xyz.lbres.kotlinutils.map.mutable.ext

/**
 * Assign all elements to have a given value
 *
 * @param value [T]: value to assign
 */
fun <S, T> MutableMap<S, T>.setAllValues(value: T) {
    keys.forEach { set(it, value) }
}
