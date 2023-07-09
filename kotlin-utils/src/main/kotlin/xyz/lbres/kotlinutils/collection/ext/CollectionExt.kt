package xyz.lbres.kotlinutils.collection.ext

import xyz.lbres.kotlinutils.generic.ext.isNotNull
import xyz.lbres.kotlinutils.generic.ext.isNull
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSetImpl

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
 * Count number of elements in the collection that are null.
 *
 * @return [Int]
 */
fun <E> Collection<E?>.countNull(): Int = count { it.isNull() }

/**
 * Count number of elements in the collection that are not null.
 *
 * @return [Int]
 */
fun <E> Collection<E?>.countNotNull(): Int = count { it.isNotNull() }

/**
 * Check if a collection is not null and is not empty.
 * Combines separate calls for checking if the collection is null and if it is empty.
 *
 * @return [Boolean]: true if the collection is not null and has size > 0, false otherwise
 */
fun <E> Collection<E>?.isNotNullOrEmpty() = !this.isNullOrEmpty()

/**
 * Get number of elements matching a specific value
 *
 * @param element [E]: value to match
 * @return [Int]: number of elements with the given value
 */
fun <E> Collection<E>.countElement(element: E) = this.count { it == element }
