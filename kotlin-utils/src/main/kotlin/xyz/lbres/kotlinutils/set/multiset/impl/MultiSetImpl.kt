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
    override val elements: Collection<E>

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        size = elements.size
        this.elements = elements
    }

    /**
     * Set size and elements from existing counts.
     */
    private constructor(counts: Map<E, Int>) {
        size = counts.values.fold(0, Int::plus)

        elements = mutableListOf()
        counts.forEach {
            repeat(it.value) { _ -> elements.add(it.key) }
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
    override fun iterator(): Iterator<E> = elements.toList().iterator()
}
