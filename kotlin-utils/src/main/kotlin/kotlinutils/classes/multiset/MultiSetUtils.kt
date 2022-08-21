package kotlinutils.classes.multiset

import kotlinutils.collection.ext.toMultiSet
import kotlinutils.collection.ext.toMutableMultiSet

/**
 * Create a MultiSet containing the given elements.
 * Runs in O(n).
 *
 * @param elements [E]: variable number of elements to include in MultiSet
 * @return [MultiSet<E>]
 */
fun <E> multiSetOf(vararg elements: E): MultiSet<E> = elements.toList().toMultiSet()

/**
 * Create a MutableMultiSet containing the given elements.
 * Runs in O(n).
 *
 * @param elements [E]: variable number of elements to include in MultiSet
 * @return [MutableMultiSet<E>]
 */
fun <E> mutableMultiSetOf(vararg elements: E): MutableMultiSet<E> = elements.toList().toMutableMultiSet()

