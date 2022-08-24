package kotlinutils.classes.multiset

import kotlinutils.collection.ext.toMultiSet
import kotlinutils.collection.ext.toMutableMultiSet

/**
 * Create a MultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MultiSet<E>]
 */
fun <E> multiSetOf(vararg elements: E): MultiSet<E> = elements.toList().toMultiSet()

/**
 * Create a MutableMultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MutableMultiSet<E>]
 */
fun <E> mutableMultiSetOf(vararg elements: E): MutableMultiSet<E> = elements.toList().toMutableMultiSet()

/**
 * Create a MultiSet containing 0 elements.
 *
 * @return [MultiSet<E>]
 */
fun <E> emptyMultiSet(): MultiSet<E> = ImmutableMultiSet(listOf())
