package xyz.lbres.kotlinutils.map.mutablemap.ext

/**
 * Assign all keys to have the same value
 *
 * @param value T: value to assign
 */
fun <S, T> MutableMap<S, T>.setAllValues(value: T) {
    keys.forEach { set(it, value) }
}
