package xyz.lbres.kotlinutils.classes.multiset

/**
 * Code related to MultiSets has been moved to the set/multiset package.
 * This file exists only to avoid breaking existing functionality that uses the class at this path.
 */

private const val packageName = "xyz.lbres.kotlinutils.set.multiset"
private const val functionMessage = "Function moved to package $packageName."
private const val interfaceMessage = "Interface moved to package $packageName."

@Deprecated(interfaceMessage, ReplaceWith("$packageName.MultiSet", "$packageName.MultiSet"), DeprecationLevel.ERROR)
interface MultiSet

@Deprecated(interfaceMessage, ReplaceWith("$packageName.MutableMultiSet", "$packageName.MutableMultiSet"), DeprecationLevel.ERROR)
interface MutableMultiSet

@Deprecated(functionMessage, ReplaceWith("$packageName.multiSetOf(elements)", "$packageName.multiSetOf"), DeprecationLevel.ERROR)
fun <E> multiSetOf(vararg elements: E): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.multiSetOf(*elements)

@Deprecated(functionMessage, ReplaceWith("$packageName.mutableMultiSetOf(elements)", "$packageName.mutableMultiSetOf"), DeprecationLevel.ERROR)
fun <E> mutableMultiSetOf(vararg elements: E): xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.mutableMultiSetOf(*elements)

@Deprecated(functionMessage, ReplaceWith("$packageName.emptyMultiSet()", "$packageName.emptyMultiSet"), DeprecationLevel.ERROR)
fun <E> emptyMultiSet(): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.emptyMultiSet()

@Deprecated(functionMessage, ReplaceWith("$packageName.MultiSet(size, init)", "$packageName.MultiSet"), DeprecationLevel.ERROR)
fun <E> MultiSet(size: Int, init: (Int) -> E): xyz.lbres.kotlinutils.set.multiset.MultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MultiSet(size, init)

@Deprecated(functionMessage, ReplaceWith("$packageName.MutableMultiSet(size, init)", "$packageName.MutableMultiSet"), DeprecationLevel.ERROR)
fun <E> MutableMultiSet(size: Int, init: (Int) -> E): xyz.lbres.kotlinutils.set.multiset.MutableMultiSet<E> = xyz.lbres.kotlinutils.set.multiset.MutableMultiSet(size, init)
