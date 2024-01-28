package xyz.lbres.kotlinutils.set.multiset.const

// final implementation of ConstMultiSet
internal class ConstMultiSetImpl<E>(initialElements: Collection<E>, initialCounts: Map<E, Int>? = null) : ConstMultiSet<E>(initialElements, initialCounts)
