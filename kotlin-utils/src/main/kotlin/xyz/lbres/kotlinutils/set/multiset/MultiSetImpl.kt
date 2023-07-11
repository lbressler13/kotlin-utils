package xyz.lbres.kotlinutils.set.multiset

/**
 * Set implementation that allows multiple occurrences of the same value.
 */
internal class MultiSetImpl<E> : AbstractMultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int

    /**
     * All distinct values contained in the MultiSet, without any counts
     */
    override val distinctValues: Set<E>
        get() {
            updateValues()
            return countsMap.keys
        }

    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be greater than 0.
     */
    override var countsMap: Map<E, Int>

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
     * Store the hash codes for all the values in the set.
     * Used to determine if any mutable values have changed.
     */
    override var hashCodes: Map<Int, Int>

    /**
     * String representation of the set.
     */
    override var string: String

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        size = elements.size
        initialElements = elements
        string = createString()

        countsMap = elements.groupBy { it }.map { it.key to it.value.size }.toMap()
        hashCodes = getCurrentHashCodes()
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        countsMap = counts
        size = counts.values.fold(0, Int::plus)

        initialElements = counts.flatMap {
            val element = it.key
            val count = it.value
            List(count) { element }
        }

        string = createString()
        hashCodes = getCurrentHashCodes()
    }

    override fun createFromCountsMap(counts: Map<E, Int>): MultiSet<E> {
        return MultiSetImpl(counts)
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): Iterator<E> = initialElements.toList().iterator()
}
