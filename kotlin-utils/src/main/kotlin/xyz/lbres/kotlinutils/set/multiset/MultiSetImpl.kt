package xyz.lbres.kotlinutils.set.multiset

/**
 * Set implementation that allows multiple occurrences of the same value.
 */
internal class MultiSetImpl<E> : AbstractMultiSetImpl<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int

//    /**
//     * Store the number of occurrences of each element in set.
//     * Counts are guaranteed to be greater than 0.
//     */
//    override var countsMap: Map<E, Int>

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

//    /**
//     * Store the hash codes for all values in the set.
//     * Used to determine if any mutable values have changed.
//     */
//    override var hashCodes: Map<Int, Int>

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        size = elements.size
        initialElements = elements

        // countsMap = elements.groupBy { it }.map { it.key to it.value.size }.toMap()
        // hashCodes = getCurrentHashCodes()
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        // countsMap = counts
        size = counts.values.fold(0, Int::plus)

        initialElements = counts.flatMap {
            val element = it.key
            val count = it.value
            List(count) { element }
        }

        // hashCodes = getCurrentHashCodes()
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
