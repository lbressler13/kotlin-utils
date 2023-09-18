package xyz.lbres.kotlinutils.set.multiset

import xyz.lbres.kotlinutils.set.multiset.impl.constimpl.ConstMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.constimpl.ConstMutableMultiSetImpl
import xyz.lbres.kotlinutils.set.multiset.impl.MutableMultiSetImpl

/**
 * Create a MultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MultiSet]<E>
 */
fun <E> multiSetOf(vararg elements: E): MultiSet<E> = MultiSetImpl(elements.toList())

/**
 * Create a MutableMultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MutableMultiSet]<E>
 */
fun <E> mutableMultiSetOf(vararg elements: E): MutableMultiSet<E> = MutableMultiSetImpl(elements.toList())

/**
 * Create a MultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MultiSet]<E>
 */
fun <E> constMultiSetOf(vararg elements: E): ConstMultiSet<E> = ConstMultiSetImpl(elements.toList())

/**
 * Create a MutableMultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MutableMultiSet]<E>
 */
fun <E> constMutableMultiSetOf(vararg elements: E): ConstMutableMultiSet<E> = ConstMutableMultiSetImpl(elements.toList())

/**
 * Create a MultiSet containing 0 elements.
 *
 * @return [MultiSet]<E>
 */
fun <E> emptyMultiSet(): MultiSet<E> = MultiSetImpl(emptyList())

/**
 * Create a MultiSet containing 0 elements.
 *
 * @return [MultiSet]<E>
 */
fun <E> emptyConstMultiSet(): ConstMultiSet<E> = ConstMultiSetImpl(emptyList())

/**
 * Create a MultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [MultiSet]<E>
 */
@Suppress("FunctionName")
fun <E> MultiSet(size: Int, init: (Int) -> E): MultiSet<E> = MultiSetImpl((0 until size).map(init))

/**
 * Create a MutableMultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [MutableMultiSet]<E>
 */
@Suppress("FunctionName")
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): MutableMultiSet<E> = MutableMultiSetImpl((0 until size).map(init))

/**
 * Create a MultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [MultiSet]<E>
 */
@Suppress("FunctionName")
fun <E> ConstMultiSet(size: Int, init: (Int) -> E): ConstMultiSet<E> = ConstMultiSetImpl((0 until size).map(init))

/**
 * Create a MutableMultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [MutableMultiSet]<E>
 */
@Suppress("FunctionName")
fun <E> MutableConstMultiSet(size: Int, init: (Int) -> E): ConstMutableMultiSet<E> = ConstMutableMultiSetImpl((0 until size).map(init))
