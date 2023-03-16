package xyz.lbres.kotlinutils.classes.multiset

import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * Code related to MultiSets has been moved to the set/multiset package.
 * This file exists only to avoid breaking existing functionality that uses the class at this path.
 */

private const val packageName = "xyz.lbres.kotlinutils.set.multiset"
private const val functionMessage = "Function moved to package $packageName."
private const val interfaceMessage = "Interface moved to package $packageName."

@Deprecated(interfaceMessage, ReplaceWith("$packageName.MultiSet", "$packageName.MultiSet"), DeprecationLevel.WARNING)
interface MultiSet<E> : Collection<E> {
    val distinctValues: Set<E>
    fun getCountOf(element: E): Int
    operator fun minus(other: MultiSet<E>): MultiSet<E>
    operator fun plus(other: MultiSet<E>): MultiSet<E>
    infix fun intersect(other: MultiSet<E>): MultiSet<E>
}

@Deprecated(interfaceMessage, ReplaceWith("$packageName.MutableMultiSet", "$packageName.MutableMultiSet"), DeprecationLevel.WARNING)
interface MutableMultiSet<E> : MultiSet<E>, MutableCollection<E>

@Deprecated(functionMessage, ReplaceWith("$packageName.multiSetOf(elements)", "$packageName.multiSetOf"), DeprecationLevel.WARNING)
fun <E> multiSetOf(vararg elements: E): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.multiSetOf(*elements)

@Deprecated(functionMessage, ReplaceWith("$packageName.mutableMultiSetOf(elements)", "$packageName.mutableMultiSetOf"), DeprecationLevel.WARNING)
fun <E> mutableMultiSetOf(vararg elements: E): xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf(*elements)

@Deprecated(functionMessage, ReplaceWith("$packageName.emptyMultiSet()", "$packageName.emptyMultiSet"), DeprecationLevel.WARNING)
fun <E> emptyMultiSet(): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.emptyMultiSet()

@Deprecated(functionMessage, ReplaceWith("$packageName.MultiSet(size, init)", "$packageName.MultiSet"), DeprecationLevel.WARNING)
fun <E> MultiSet(size: Int, init: (Int) -> E): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MultiSet(size, init)

@Deprecated(functionMessage, ReplaceWith("$packageName.MutableMultiSet(size, init)", "$packageName.MutableMultiSet"), DeprecationLevel.WARNING)
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MutableMultiSet(size, init)
