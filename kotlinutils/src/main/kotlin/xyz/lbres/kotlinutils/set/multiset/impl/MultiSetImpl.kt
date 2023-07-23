package xyz.lbres.kotlinutils.set.multiset.impl

import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * Set implementation that allows multiple occurrences of the same value.
 */
internal class MultiSetImpl<E> : AbstractMultiSetImpl<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int

    /**
     * Elements in the set.
     */
    override val values: Collection<E>

    /**
     * Initialize set from a collection of values.
     */
    constructor(elements: Collection<E>) {
        size = elements.size
        this.values = elements
    }

    /**
     * Initialize set from existing counts.
     */
    private constructor(counts: Map<E, Int>) {
        size = counts.values.fold(0, Int::plus)

        values = mutableListOf()
        counts.forEach {
            repeat(it.value) { _ -> values.add(it.key) }
        }
    }

    /**
     * Initialize a new MultiSet from existing counts.
     */
    override fun createFromCounts(counts: Map<E, Int>): MultiSet<E> = MultiSetImpl(counts)

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): Iterator<E> = values.toList().iterator()
}
