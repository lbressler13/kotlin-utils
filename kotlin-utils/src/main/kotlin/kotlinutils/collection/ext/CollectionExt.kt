package kotlinutils.collection.ext

import kotlinutils.classes.multiset.MultiSet
import kotlinutils.classes.multiset.MutableMultiSet
import kotlinutils.int.ext.isZero

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

/**
 * Filter an integer collection to contain only elements that do not equal zero.
 *
 * @return [Collection<Int>]: collection containing the same values as [this], except any elements with value 0.
 */
fun Collection<Int>.filterNotZero() = filterNot { it.isZero() }
