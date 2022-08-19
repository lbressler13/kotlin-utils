package kotlinutils.classes.multiset

import kotlinutils.collection.ext.toMultiSet

/**
 * Create a MultiSet containing the given elements.
 * Runs in O(n).
 *
 * @param elements [T]: variable number of elements to include in MultiSet
 * @return [MultiSet<T>]
 */
fun <T> multiSetOf(vararg elements: T): MultiSet<T> = elements.toList().toMultiSet()
