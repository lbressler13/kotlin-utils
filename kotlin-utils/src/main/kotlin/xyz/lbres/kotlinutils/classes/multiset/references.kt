package xyz.lbres.kotlinutils.classes.multiset

/**
 * Code related to MultiSets has been moved to the set/multiset package.
 * This file exists only to avoid breaking existing functionality that uses the class at this path.
 */

typealias MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MultiSet<E>
typealias MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E>
fun <E> multiSetOf(vararg elements: E): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.multiSetOf(*elements)
fun <E> mutableMultiSetOf(vararg elements: E): xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf(*elements)
fun <E> emptyMultiSet(): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.emptyMultiSet()
fun <E> MultiSet(size: Int, init: (Int) -> E): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MultiSet(size, init)
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MutableMultiSet(size, init)
