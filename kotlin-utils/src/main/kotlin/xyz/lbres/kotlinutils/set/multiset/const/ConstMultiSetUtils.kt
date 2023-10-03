package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.internal.constants.Suppressions

/**
 * Create a ConstMultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [ConstMultiSet]<E>
 */
fun <E> constMultiSetOf(vararg elements: E): ConstMultiSet<E> = ConstMultiSetImpl(elements.toList())

/**
 * Create a ConstMutableMultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [ConstMutableMultiSet]<E>
 */
fun <E> constMutableMultiSetOf(vararg elements: E): ConstMutableMultiSet<E> = ConstMutableMultiSetImpl(elements.toList())

/**
 * Create a ConstMultiSet containing 0 elements.
 *
 * @return [ConstMultiSet]<E>
 */
fun <E> emptyConstMultiSet(): ConstMultiSet<E> = ConstMultiSetImpl(emptyList())

/**
 * Create a ConstMultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [ConstMultiSet]<E>
 */
@Suppress(Suppressions.FUNCTION_NAME)
fun <E> ConstMultiSet(size: Int, init: (Int) -> E): ConstMultiSet<E> = ConstMultiSetImpl((0 until size).map(init))

/**
 * Create a ConstMutableMultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init ([Int]) -> E: initialization function, used to create each element based on its index
 * @return [ConstMutableMultiSet]<E>
 */
@Suppress(Suppressions.FUNCTION_NAME)
fun <E> ConstMutableMultiSet(size: Int, init: (Int) -> E): ConstMutableMultiSet<E> = ConstMutableMultiSetImpl((0 until size).map(init))
