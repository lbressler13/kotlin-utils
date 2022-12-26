package xyz.lbres.kotlinutils.list.mutablelist.ext

import xyz.lbres.kotlinutils.collection.mutable.ext.popRandom

/**
 * Remove a random element from list and return it.
 * Has been moved to a MutableCollection method.
 *
 * @return [T]?: an element from the list, or `null` if the list is empty
 */
fun <T> MutableList<T>.popRandom(): T? = popRandom()
