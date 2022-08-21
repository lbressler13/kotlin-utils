package kotlinutils.collection.ext

import kotlinutils.classes.multiset.MultiSet
import kotlinutils.classes.multiset.MutableMultiSet

/**
 * Create a MultiSet with the elements in the current collection.
 *
 * @return [MultiSet<E>]
 */
fun <E> Collection<E>.toMultiSet() = MultiSet(this)

/**
 * Create a MutableMultiSet with the elements in the current collection.
 *
 * @return [MultiSet<E>]
 */
fun <E> Collection<E>.toMutableMultiSet() = MutableMultiSet(this)
