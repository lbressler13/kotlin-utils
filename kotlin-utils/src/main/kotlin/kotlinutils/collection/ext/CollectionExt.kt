package kotlinutils.collection.ext

import kotlinutils.classes.multiset.MultiSet
import kotlinutils.classes.multiset.MultiSetImpl
import kotlinutils.classes.multiset.MutableMultiSet
import kotlinutils.classes.multiset.MutableMultiSetImpl
import kotlinutils.int.ext.isZero

/**
 * Create a MultiSet with the elements in the current collection.
 *
 * @return [MultiSet<E>]
 */
fun <E> Collection<E>.toMultiSet(): MultiSet<E> = MultiSetImpl(this)

/**
 * Create a MutableMultiSet with the elements in the current collection.
 *
 * @return [MultiSet<E>]
 */
fun <E> Collection<E>.toMutableMultiSet(): MutableMultiSet<E> = MutableMultiSetImpl(this)

/**
 * Filter an integer collection to contain only elements that do not equal zero.
 *
 * @return [Collection<Int>]: collection containing the same values as [this], except any elements with value 0.
 */
fun Collection<Int>.filterNotZero() = filterNot { it.isZero() }

/**
 * Count number of elements in the collection that are null.
 *
 * @return [Int]
 */
fun <E> Collection<E?>.countNull(): Int = count { it == null }

/**
 * Count number of elements in the collection that are not null.
 *
 * @return [Int]
 */
fun <E> Collection<E?>.countNotNull(): Int = count { it != null }

/**
 * Check if a collection is not null and is not empty.
 * Combines separate calls for checking if the collection is null and if it is empty.
 *
 * @return [Boolean]: true if the collection is not null and has size > 0, false otherwise
 */
fun <E> Collection<E>?.isNotNullOrEmpty() = !this.isNullOrEmpty()
