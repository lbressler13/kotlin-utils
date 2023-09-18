package xyz.lbres.kotlinutils.set.multiset.impl.constimpl

import xyz.lbres.kotlinutils.list.listOfValue
import xyz.lbres.kotlinutils.set.multiset.ConstMultiSet

/**
 * Multi set implementation where values are assumed to be constant.
 * Behavior is not defined if mutable values are changed.
 */
internal class ConstMultiSetImpl<E> : AbstractConstMultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int

    override val distinctValues: Set<E>

    override val counts: Map<E, Int>

    private val elements: List<E>

    /**
     * Initialize set from a collection of values.
     */
    internal constructor(elements: Collection<E>) {
        size = elements.size

        counts = elements.groupBy { it }.map {
            it.key to it.value.size
        }.toMap()
        distinctValues = counts.keys

        this.elements = elements.toList()
    }

    /**
     * Initialize set from existing counts.
     */
    private constructor(counts: Map<E, Int>) {
        this.counts = counts
        size = counts.values.fold(0, Int::plus)
        distinctValues = counts.keys

        this.elements = counts.keys.fold(emptyList()) { acc, element ->
            acc + listOfValue(counts[element]!!, element)
        }
    }

    override fun createFromCounts(counts: Map<E, Int>): ConstMultiSet<E> {
        return ConstMultiSetImpl(counts)
    }

    override fun iterator(): Iterator<E> = elements.iterator()
}
