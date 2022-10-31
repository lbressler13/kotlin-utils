package xyz.lbres.kotlinutils.classes.multiset

/**
 * Create a MultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MultiSet<E>]
 */
fun <E> multiSetOf(vararg elements: E): MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.multiSetOf(*elements)

/**
 * Create a MutableMultiSet containing the given elements.
 *
 * @param elements [E]: variable number of elements to include in set
 * @return [MutableMultiSet<E>]
 */
fun <E> mutableMultiSetOf(vararg elements: E): MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf(*elements)

/**
 * Create a MultiSet containing 0 elements.
 *
 * @return [MultiSet<E>]
 */
fun <E> emptyMultiSet(): MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.emptyMultiSet()

/**
 * Create a MultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init [(Int) -> E]: initialization function, used to create each element based on its index
 * @return [MultiSet<E>]
 */
fun <E> MultiSet(size: Int, init: (Int) -> E): MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MultiSet(size, init)

/**
 * Create a MutableMultiSet of a given size, using [init] to generate each element in the set.
 *
 * @param size [Int]: size of set to create
 * @param init [(Int) -> E]: initialization function, used to create each element based on its index
 * @return [MutableMultiSet<E>]
 */
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MutableMultiSet(size, init)
