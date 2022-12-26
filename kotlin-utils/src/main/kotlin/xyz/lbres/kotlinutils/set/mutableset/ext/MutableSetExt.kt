package xyz.lbres.kotlinutils.set.mutableset.ext

import xyz.lbres.kotlinutils.collection.mutable.ext.popRandom

/**
 * Remove a random element from set and return it.
 * Has been moved to a MutableCollection method.
 *
 * @return [T]?: an element from the set, or null if the set is empty
 */
fun <T> MutableSet<T>.popRandom(): T? = popRandom()
