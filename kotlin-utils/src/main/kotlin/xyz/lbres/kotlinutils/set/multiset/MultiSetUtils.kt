package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.collection.ext.toMultiSet
import xyz.lbres.kotlinutils.collection.ext.toMutableMultiSet

/**
 * Create a MultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MultiSet]<E>
 */
fun <E> multiSetOf(vararg elements: E): MultiSet<E> = elements.toList().toMultiSet()

/**
 * Create a MutableMultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MutableMultiSet]<E>
 */
fun <E> mutableMultiSetOf(vararg elements: E): MutableMultiSet<E> = elements.toList().toMutableMultiSet()

/**
 * Create a MultiSet containing 0 elements.
 *
 * @return [MultiSet]<E>
 */
fun <E> emptyMultiSet(): MultiSet<E> = MultiSetImpl(emptyList())

/**
 * Create a MultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [MultiSet]<E>
 */
fun <E> MultiSet(size: Int, init: (Int) -> E): MultiSet<E> = MultiSetImpl((0 until size).map(init))

/**
 * Create a MutableMultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [MutableMultiSet]<E>
 */
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): MutableMultiSet<E> = MutableMultiSetImpl((0 until size).map(init))
