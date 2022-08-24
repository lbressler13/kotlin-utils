package kotlinutils.classes.multiset

/**
 * Set implementation that allows multiple occurrences of the same value.
 */
class MultiSet<E> internal constructor(elements: Collection<E>) : Set<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int = elements.size

    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be greater than 0.
     */
    private val countsMap: HashMap<E, Int>

    /**
     * The initial elements that were passed to the constructor.
     */
    private val initialElements: Collection<E> = elements

    /**
     * String representation of the set.
     */
    private val string: String

    /**
     * Initialize stored variables.
     */
    init {
        // init string
        string = if (size == 0) {
            "[]"
        } else {
            val elementsString = elements.joinToString(", ")
            "[$elementsString]"
        }

        // init counts
        val mutableMap: MutableMap<E, Int> = mutableMapOf()

        for (element in elements) {
            val currentCount = mutableMap[element] ?: 0
            mutableMap[element] = currentCount + 1
        }

        countsMap = HashMap(mutableMap)
    }

    /**
     * Constructor that creates a MultiSet of a given size, using [initializeElement] to generate each element in the set.
     *
     * @param size [Int]: size of set to create
     * @param initializeElement [(Int) -> E]: initialization function, used to create each element based on its index
     */
    constructor(size: Int, initializeElement: (Int) -> E) : this((0 until size).map(initializeElement))

    /**
     * Determine if an element is contained in the current set.
     *
     * @param element [E]
     * @return [Boolean]: true if [element] is in the set, false otherwise
     */
    override fun contains(element: E): Boolean = countsMap.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current set.
     * If [elements] contains multiple occurrences of the same value, the function will check if this set contains at least as many occurrences as [elements].
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if the current set contains at least as many occurrences of each value as [elements], false otherwise
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val newSet = MultiSet(elements)
        return newSet.countsMap.all { countsMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = countsMap.isEmpty()

    /**
     * Get the number of occurrences of a given element in the current set.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    fun getCountOf(element: E): Int = countsMap.getOrDefault(element, 0)

    /**
     * If two MultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any?]
     * @return [Boolean]: true if [other] is a non-null MultiSet which contains the same values as the current set, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        return countsMap == other.countsMap
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator<E>]
     */
    override fun iterator(): Iterator<E> = initialElements.iterator()

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String = string

    override fun hashCode(): Int = listOf("MultiSet", countsMap).hashCode()
}
