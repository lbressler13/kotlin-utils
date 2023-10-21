package xyz.lbres.kotlinutils.collection.ext

import xyz.lbres.kotlinutils.iterable.ext.countElement
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl

/**
 * Create a MultiSet with the elements in the current collection.
 *
 * @return [MultiSet]<E>
 */
fun <E> Collection<E>.toMultiSet(): MultiSet<E> = MultiSetImpl(this)

/**
 * Create a MutableMultiSet with the elements in the current collection.
 *
 * @return [MutableMultiSet]<E>
 */
fun <E> Collection<E>.toMutableMultiSet(): MutableMultiSet<E> = MutableMultiSetImpl(this)

/**
 * Create a ConstMultiSet with the elements in the current collection.
 *
 * @return [ConstMultiSet]<E>
 */
fun <E> Collection<E>.toConstMultiSet(): ConstMultiSet<E> = ConstMultiSetImpl(this)

/**
 * Create a ConstMutableMultiSet with the elements in the current collection.
 *
 * @return [ConstMutableMultiSet]<E>
 */
fun <E> Collection<E>.toMutableConstMultiSet(): ConstMutableMultiSet<E> = ConstMutableMultiSetImpl(this)

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

/**
 * Transform elements in collection, and keep only transformed values which match a given predicate
 *
 * @param transform (E) -> T: function to transform elements
 * @param predicate (E) -> [Boolean]: predicate to use for filtering
 * @return [List]<T>: list containing transformed values for which [predicate] returns `true`
 */
fun <E, T> Collection<E>.mapFilter(transform: (E) -> T, predicate: (T) -> Boolean): List<T> {
    val result: MutableList<T> = mutableListOf()
    forEach {
        val transformed = transform(it)
        if (predicate(transformed)) {
            result.add(transformed)
        }
    }

    return result.toList()
}
