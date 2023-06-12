package xyz.lbres.kotlinutils.list.mutablelist.ext

import xyz.lbres.kotlinutils.collection.mutable.ext.popRandom

/**
 * Remove a random element from list and return it.
 *
 * @return [T]?: an element from the list, or `null` if the list is empty
 */
fun <T> MutableList<T>.popRandom(): T? = popRandom()

/**
 * Assign all elements to have a given value
 *
 * @param value [T]: value to assign
 */
fun <T> MutableList<T>.setAllValues(value: T) {
    indices.forEach { set(it, value) }
}
