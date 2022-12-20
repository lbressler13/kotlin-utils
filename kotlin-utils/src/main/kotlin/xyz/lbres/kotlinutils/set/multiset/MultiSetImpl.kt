package xyz.lbres.kotlinutils.set.multiset

import kotlin.math.max

/**
 * Set implementation that allows multiple occurrences of the same value.
 */
internal class MultiSetImpl<E> : MultiSet<E> {
    /**
     * Number of elements in set.
     */
    override val size: Int

    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be greater than 0.
     */
    private val countsMap: HashMap<E, Int>

    /**
     * The initial elements that were passed to the constructor.
     */
    private val initialElements: Collection<E>

    /**
     * String representation of the set.
     */
    private val string: String

    /**
     * Initialize stored variables from a collection of values.
     */
    constructor(elements: Collection<E>) {
        size = elements.size
        initialElements = elements
        string = createString()

        // init counts
        val mutableMap: MutableMap<E, Int> = mutableMapOf()

        for (element in initialElements) {
            val currentCount = mutableMap[element] ?: 0
            mutableMap[element] = currentCount + 1
        }

        countsMap = HashMap(mutableMap)
    }

    /**
     * Initialize stored variables from an existing counts map.
     */
    private constructor(counts: HashMap<E, Int>) {
        countsMap = counts
        size = counts.values.fold(0, Int::plus)

        initialElements = counts.flatMap {
            val element = it.key
            val count = it.value
            List(count) { element }
        }

        string = createString()
    }

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

        val newSet = MultiSetImpl(elements)
        return newSet.countsMap.all { countsMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    override operator fun minus(other: MultiSet<E>): MultiSet<E> {
        val newCounts: Map<E, Int> = countsMap.map {
            val element = it.key
            val count = it.value
            val otherCount = other.getCountOf(element)
            element to max(count - otherCount, 0)
        }.filter { it.second > 0 }.toMap()

        return MultiSetImpl(HashMap(newCounts))
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
    override fun getCountOf(element: E): Int = countsMap.getOrDefault(element, 0)

    /**
     * If two ImmutableMultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any?]
     * @return [Boolean]: true if [other] is a non-null ImmutableMultiSet which contains the same values as the current set, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MultiSetImpl<*>) {
            return false
        }

        return countsMap == other.countsMap
    }

    /**
     * Create the static string representation of the set.
     */
    private fun createString(): String {
        if (initialElements.isEmpty()) {
            return "[]"
        }

        val elementsString = initialElements.joinToString(", ")
        return "[$elementsString]"
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

    override fun hashCode(): Int = listOf("ImmutableMultiSet", countsMap).hashCode()
}
