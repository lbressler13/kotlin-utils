package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.list.listOfValue
import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * Multi set implementation where values are assumed to be constant.
 * Behavior is not defined if mutable values are changed.
 */
internal class ConstMultiSetImpl<E> : AbstractConstMultiSetImpl<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int

    override val distinctValues: Set<E>

    override val counts: HashMap<E, Int>

    private val elements: Collection<E>

    /**
     * Initialize set from a collection of values.
     */
    internal constructor(elements: Collection<E>) {
        size = elements.size

        val map = elements.groupBy { it }.map {
            it.key to it.value.size
        }.toMap()
        counts = map as HashMap
        distinctValues = counts.keys

        this.elements = elements
        initializeValues(elements)
    }

    /**
     * Initialize set from existing counts.
     */
    private constructor(counts: HashMap<E, Int>) {
        this.counts = counts
        size = counts.values.fold(0, Int::plus)
        distinctValues = counts.keys

        this.elements = counts.keys.fold(emptyList()) { acc, element ->
            acc + listOfValue(counts[element]!!, element)
        }
    }

    override fun createFromCounts(counts: HashMap<E, Int>): MultiSet<E> {
        return ConstMultiSetImpl(counts)
    }

    override fun iterator(): Iterator<E> = elements.iterator()
}