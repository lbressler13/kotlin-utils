package xyz.lbres.kotlinutils.set.multiset.const

import xyz.lbres.kotlinutils.generic.ext.ifNull
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.utils.CountsMap
import xyz.lbres.kotlinutils.set.multiset.utils.countsToString
import xyz.lbres.kotlinutils.set.multiset.utils.createCountsMap

// final implementation of ConstMultiSet
internal class ConstMultiSetImpl<E>(private val elements: Collection<E>, initialCounts: CountsMap<E>? = null) : ConstMultiSet<E>(elements, initialCounts?.counts) {
    private val manager: ConstMultiSetManager<E>
    override val size: Int = elements.size
    override val distinctValues: Set<E>
    private val string: String
    val counts: CountsMap<E>

    init {
        counts = initialCounts.ifNull { CountsMap.from(elements) }
        distinctValues = counts.distinct
        manager = ConstMultiSetManager(counts)
        string = counts.toString()
    }

    override fun getCountOf(element: E): Int = counts.getCountOf(element)
    override fun contains(element: E): Boolean = counts.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = counts.containsAll(elements)

    override fun plus(other: MultiSet<E>): MultiSet<E> = manager.plus(other)
    override fun minus(other: MultiSet<E>): MultiSet<E> = manager.minus(other)
    override fun intersect(other: MultiSet<E>): MultiSet<E> = manager.intersect(other)

    override fun plusC(other: ConstMultiSet<E>): ConstMultiSet<E> = manager.plusC(other)
    override fun minusC(other: ConstMultiSet<E>): ConstMultiSet<E> = manager.minusC(other)
    override fun intersectC(other: ConstMultiSet<E>): ConstMultiSet<E> = manager.intersectC(other)

    override fun isEmpty(): Boolean = counts.isEmpty()
    override fun iterator(): Iterator<E> = elements.iterator()
    override fun hashCode(): Int = manager.getHashCode()
    override fun equals(other: Any?): Boolean = manager.equalsSet(other)
    override fun toString(): String = string
}
