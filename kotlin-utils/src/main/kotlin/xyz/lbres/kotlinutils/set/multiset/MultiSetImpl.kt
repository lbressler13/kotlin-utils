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
     * Mutable parameter for all distinct values contained in the MultiSet, without any counts
     */
    private var _distinctValues: Set<E> = emptySet()

    /**
     * All distinct values contained in the MultiSet, without any counts
     */
    override val distinctValues: Set<E>
        get() {
            updateValues()
            return _distinctValues
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
        _distinctValues = countsMap.keys
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: Map<E, Int>) {
        countsMap = counts
        size = counts.values.fold(0, Int::plus)
        _distinctValues = counts.keys

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
     * Create the static string representation of the set.
     * Stored in a helper so it can be reused in both constructors.
     *
     * @return [String]
     */
    override fun createString(): String {
        if (initialElements.isEmpty()) {
            return "[]"
        }

        val elementsString = initialElements.joinToString(", ")
        return "[$elementsString]"
    }

    /**
     * If the hash code values are changed, update all properties related to the values of the set
     */
    override fun updateValues() {
        val currentCodes = getCurrentHashCodes()
        if (currentCodes != hashCodes) {
            string = createString()

            countsMap = initialElements.groupBy { it }.map { it.key to it.value.size }.toMap()
            hashCodes = currentCodes
            _distinctValues = countsMap.keys
        }
    }

    /**
     * Get hash codes for the elements
     *
     * @return [Map]<Int, Int>: hash codes for all elements in the set
     */
    override fun getCurrentHashCodes(): Map<Int, Int> {
        val hashCodeCounts: MutableMap<Int, Int> = mutableMapOf()

        initialElements.forEach {
            val code = it.hashCode()
            hashCodeCounts[code] = (hashCodeCounts[code] ?: 0) + 1
        }

        return hashCodeCounts
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator]<E>
     */
    override fun iterator(): Iterator<E> = initialElements.toList().iterator()
}
