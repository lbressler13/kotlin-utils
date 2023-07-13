package xyz.lbres.kotlinutils.set.multiset

/**
 * Set implementation that allows multiple occurrences of the same value.
 */
internal class MultiSetImpl<E> : AbstractMultiSetImpl<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int

    /**
     * The initial elements that were passed to the constructor.
     */
    private val initialElements: Collection<E>

    /**
     * Elements used to generate [hashCodes].
     * Used to determine if mutable values have changed.
     */
    override val hashElements: Collection<E>
        get() = initialElements

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        size = elements.size
        initialElements = elements
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        size = counts.values.fold(0, Int::plus)

        initialElements = counts.flatMap {
            val element = it.key
            val count = it.value
            List(count) { element }
        }
    }

    /**
     * Initialize a new MultiSet from an existing counts map.
     */
    override fun createFromCountsMap(counts: Map<E, Int>): MultiSet<E> = MultiSetImpl(counts)

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): Iterator<E> = initialElements.toList().iterator()
}
