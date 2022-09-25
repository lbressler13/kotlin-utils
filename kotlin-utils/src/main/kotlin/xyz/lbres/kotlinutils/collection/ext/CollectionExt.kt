package xyz.lbres.kotlinutils.collection.ext

import xyz.lbres.kotlinutils.classes.multiset.MultiSet
import xyz.lbres.kotlinutils.classes.multiset.MultiSetImpl
import xyz.lbres.kotlinutils.classes.multiset.MutableMultiSet
import xyz.lbres.kotlinutils.classes.multiset.MutableMultiSetImpl
import xyz.lbres.kotlinutils.int.ext.isZero

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
