package xyz.lbres.kotlinutils.classes.multiset

/**
 * Code related to MultiSets has been moved to the set/multiset package.
 * This file exists only to avoid breaking existing functionality that uses the class at this path.
 */

private const val packageName = "xyz.lbres.kotlinutils.set.multiset"
private const val functionMessage = "Function moved to package $packageName."
private const val classMessage = "Class moved to package $packageName."

@Deprecated(classMessage, ReplaceWith("$packageName.MultiSet", "xyz"), DeprecationLevel.WARNING)
typealias MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MultiSet<E>

@Deprecated(classMessage, ReplaceWith("$packageName.MutableMultiSet", "xyz"), DeprecationLevel.WARNING)
typealias MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E>

@Deprecated(functionMessage, ReplaceWith("$packageName.multiSetOf(elements)", "xyz"), DeprecationLevel.WARNING)
fun <E> multiSetOf(vararg elements: E): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.multiSetOf(*elements)

@Deprecated(functionMessage, ReplaceWith("$packageName.mutableMultiSetOf(elements)", "xyz"), DeprecationLevel.WARNING)
fun <E> mutableMultiSetOf(vararg elements: E): xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf(*elements)

@Deprecated(functionMessage, ReplaceWith("$packageName.emptyMultiSet()", "xyz"), DeprecationLevel.WARNING)
fun <E> emptyMultiSet(): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.emptyMultiSet()

@Deprecated(functionMessage, ReplaceWith("$packageName.MultiSet(size, init)", "xyz"), DeprecationLevel.WARNING)
fun <E> MultiSet(size: Int, init: (Int) -> E): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MultiSet(size, init)

@Deprecated(functionMessage, ReplaceWith("$packageName.MutableMultiSet(size, init)", "xyz"), DeprecationLevel.WARNING)
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MutableMultiSet(size, init)
