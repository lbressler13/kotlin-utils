package xyz.lbres.kotlinutils.classes.multiset

/**
 * Code related to MultiSets has been moved to the set/multiset package.
 * This file exists only to avoid breaking existing functionality that uses the class at this path.
 */

private const val packageName = "xyz.lbres.kotlinutils.set.multiset"
private const val functionMessage = "Function moved to package $packageName."
private const val interfaceMessage = "Interface moved to package $packageName."

@Deprecated(interfaceMessage, ReplaceWith("$packageName.MultiSet", "$packageName.MultiSet"), DeprecationLevel.WARNING)
interface MultiSet<E> : xyz.lbres.kotlinutils.set.multiset.MultiSet<E>

@Deprecated(interfaceMessage, ReplaceWith("$packageName.MutableMultiSet", "$packageName.MutableMultiSet"), DeprecationLevel.WARNING)
interface MutableMultiSet<E> : xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E>

@Deprecated(functionMessage, ReplaceWith("$packageName.multiSetOf(elements)", "$packageName.multiSetOf"), DeprecationLevel.WARNING)
fun <E> multiSetOf(vararg elements: E): MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.multiSetOf(*elements) as MultiSet

@Deprecated(functionMessage, ReplaceWith("$packageName.mutableMultiSetOf(elements)", "$packageName.mutableMultiSetOf"), DeprecationLevel.WARNING)
fun <E> mutableMultiSetOf(vararg elements: E): MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf(*elements) as MutableMultiSet

@Deprecated(functionMessage, ReplaceWith("$packageName.emptyMultiSet()", "$packageName.emptyMultiSet"), DeprecationLevel.WARNING)
fun <E> emptyMultiSet(): MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.emptyMultiSet<E>() as MultiSet

@Deprecated(functionMessage, ReplaceWith("$packageName.MultiSet(size, init)", "$packageName.MultiSet"), DeprecationLevel.WARNING)
fun <E> MultiSet(size: Int, init: (Int) -> E): MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MultiSet(size, init) as MultiSet

@Deprecated(functionMessage, ReplaceWith("$packageName.MutableMultiSet(size, init)", "$packageName.MutableMultiSet"), DeprecationLevel.WARNING)
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MutableMultiSet(size, init) as MutableMultiSet
