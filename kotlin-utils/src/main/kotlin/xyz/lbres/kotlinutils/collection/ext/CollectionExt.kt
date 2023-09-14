package xyz.lbres.kotlinutils.collection.ext

import xyz.lbres.kotlinutils.iterable.ext.countElement
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MutableConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MutableStandardMultiSet
import xyz.lbres.kotlinutils.set.multiset.impl.StandardMultiSet

/**
 * Create a MultiSet with the elements in the current collection.
 *
 * @return [MultiSet<E>]
 */
fun <E> Collection<E>.toMultiSet(): MultiSet<E> = StandardMultiSet(this)

/**
 * Create a MutableMultiSet with the elements in the current collection.
 *
 * @return [MultiSet<E>]
 */
fun <E> Collection<E>.toMutableMultiSet(): MutableMultiSet<E> = MutableStandardMultiSet(this)

/**
 * Create a MultiSet with the elements in the current collection.
 *
 * @return [MultiSet<E>]
 */
fun <E> Collection<E>.toConstMultiSet(): MultiSet<E> = ConstMultiSetImpl(this)

/**
 * Create a MutableMultiSet with the elements in the current collection.
 *
 * @return [MultiSet<E>]
 */
fun <E> Collection<E>.toMutableConstMultiSet(): MutableMultiSet<E> = MutableConstMultiSetImpl(this)

/**
 * Count number of elements in the collection that are null.
 *
 * @return [Int]
 */
fun <E> Collection<E?>.countNull(): Int = countElement(null)

/**
 * Count number of elements in the collection that are not null.
 *
 * @return [Int]
 */
fun <E> Collection<E?>.countNotNull(): Int = size - countNull()

/**
 * Check if a collection is not null and is not empty.
 * Combines separate calls for checking if the collection is null and if it is empty.
 *
 * @return [Boolean]: true if the collection is not null and has size > 0, false otherwise
 */
fun <E> Collection<E>?.isNotNullOrEmpty() = !this.isNullOrEmpty()
