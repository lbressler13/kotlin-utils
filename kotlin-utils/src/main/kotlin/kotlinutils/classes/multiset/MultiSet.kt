package kotlinutils.classes.multiset

/**
 * Set implementation that allows multiple occurrences of the same value.
 */
class MultiSet<E> internal constructor(elements: Collection<E>) : Set<E> {
    /**
     * Number of elements in set
     */
    override val size: Int

    /**
     * Store the number of occurrences of each element in set.
     * Because set is not mutable, counts are guaranteed to be greater than 0.
     */
    private val countsMap: HashMap<E, Int>

    /**
     * Iterator for the set, is created and stored the first time it's requested
     */
    private var iter: Iterator<E>? = null

    /**
     * Initialize size and countsMap from input elements.
     * Runs in O(n).
     */
    init {
        size = elements.size

        val mutableMap: MutableMap<E, Int> = mutableMapOf()

        for (value in elements) {
            val currentCount = mutableMap[value] ?: 0
            mutableMap[value] = currentCount + 1
        }

        countsMap = HashMap(mutableMap)
    }

    /**
     * Constructor that creates a MultiSet of a given size, using the init function to generate each element
     * Runs in O(n), in addition to the runtime of class init function
     *
     * @param size [Int]: size of MultiSet to create
     * @param initializeElement [(Int) -> E]: initialization function, used to create each element based on its index
     */
    constructor(size: Int, initializeElement: (Int) -> E) : this((0 until size).map(initializeElement))

    /**
     * Determine if an element is contained in the current MultiSet.
     * Runs in O(1).
     *
     * @param element [E]
     * @return [Boolean]: `true` if [element] is in the MultiSet, `false` otherwise
     */
    override fun contains(element: E): Boolean = countsMap.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current MultiSet.
     * If [elements] has repeats of a single element,
     * this function checks if the MultiSet has at least as many occurrence as the input collection.
     * Runs in O(e), where e is the size of [elements].
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: `true` if the current MultiSet contains at least as many occurrences of each value as [elements], `false` otherwise
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val newSet = MultiSet(elements)
        return newSet.countsMap.all { countsMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    /**
     * If the current MultiSet contains 0 elements.
     * Runs in O(1).
     *
     * @return [Boolean]: `true` if the MultiSet contains 0 elements, `false` otherwise
     */
    override fun isEmpty(): Boolean = countsMap.isEmpty()

    /**
     * Get the number of occurrences of a given element in the current MultiSet.
     * Runs in O(1).
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    fun getCountOf(element: E): Int = countsMap[element] ?: 0

    /**
     * If two MultiSets contain the same elements, with the same number of occurrences per element.
     * Runs in O(n).
     *
     * @param other [Any?]
     * @return [Boolean]: `true` if [other] is a non-null MultiSet which contains the same values, `false` otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSet<*>) {
            return false
        }

        return countsMap == other.countsMap
    }

    /**
     * Create an iterator for the elements in this MultiSet.
     * Runs in O(n) the first time, and O(1) for repeat calls
     *
     * @return [Iterator<E>]
     */
    override fun iterator(): Iterator<E> {
        if (iter == null) {
            val list: MutableList<E> = mutableListOf()
            countsMap.forEach {
                val element = it.key
                val count = it.value
                repeat(count) { list.add(element) }
            }

            // store iterator to be used for future calls
            iter = list.iterator()
        }

        return iter!!
    }

    override fun hashCode(): Int = listOf("MultiSet", countsMap).hashCode()
}
