package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.generic.ext.ifNull
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.utils.countsToString
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap

// final implementation of ConstMultiSet
internal class ConstMultiSetImpl<E>(initialElements: Collection<E>, initialCounts: Map<E, Int>? = null) : ConstMultiSet<E>(initialElements, initialCounts) {
    private val manager: ConstMultiSetManager<E>
    override val size: Int = initialElements.size
    override val distinctValues: Set<E>
    private val counts: Map<E, Int>
    private val string: String

    init {
        counts = initialCounts.ifNull { createCountsMap(initialElements) }
        distinctValues = counts.keys
        manager = ConstMultiSetManager(initialElements, counts)
        string = countsToString(counts)
    }

    override fun getCountOf(element: E): Int = manager.getCountOf(element)
    override fun contains(element: E): Boolean = manager.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = manager.containsAll(elements)

    override fun plus(other: MultiSet<E>): MultiSet<E> = manager.plus(other)
    override fun minus(other: MultiSet<E>): MultiSet<E> = manager.minus(other)
    override fun intersect(other: MultiSet<E>): MultiSet<E> = manager.intersect(other)

    override fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> = manager.plusC(other)
    override fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> = manager.minusC(other)
    override fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> = manager.intersectC(other)

    override fun isEmpty(): Boolean = manager.isEmpty()
    override fun iterator(): Iterator<E> = manager.getIterator()
    override fun hashCode(): Int = manager.getHashCode()
    override fun toString(): String = string
}
