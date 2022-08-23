package kotlinutils.classes.multiset

import kotlinutils.int.ext.isZero
import java.lang.Integer.min

class MutableMultiSet<E> internal constructor(elements: Collection<E>) : MutableSet<E> {
    /**
     * Number of elements in set. References a mutable variable.
     */
    override val size: Int
        get() = storedSize

    /**
     * Number of elements in the set.
     */
    private var storedSize: Int = 0

    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be non-negative.
     */
    private val countsMap: MutableMap<E, Int>

    /**
     * A list containing all elements in the set, which is updated when updateList is called.
     */
    private val list: MutableList<E>

    /**
     * Flag to indicate if the elements in the list are up-to-date with the current countsMap.
     */
    private var listUpdated = false

    /**
     * String representation of the set.
     */
    private var string: String

    /**
     * Initialize stored variables from input elements.
     */
    init {
        storedSize = elements.size

        countsMap = mutableMapOf()

        for (value in elements) {
            countsMap[value] = getCountOf(value) + 1
        }

        // string and list are both updated in updateList
        list = mutableListOf()
        string = ""
        updateList()
    }

    /**
     * Constructor that creates a MutableMultiSet of a given size, using the init function to generate each element.
     *
     * @param size [Int]: size of MultiSet to create
     * @param initializeElement [(Int) -> E]: initialization function, used to create each element based on its index
     */
    constructor(size: Int, initializeElement: (Int) -> E) : this((0 until size).map(initializeElement))

    /**
     * Adds one occurrence specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been added successfully, `false` otherwise
     */
    override fun add(element: E): Boolean {
        countsMap[element] = getCountOf(element) + 1
        storedSize++
        listUpdated = false
        return true
    }

    /**
     * Adds all specified elements to the set.
     * If [elements] contains multiple occurrences of the same value, it will be added multiple times.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: `true` if any elements have been added successfully, `false` otherwise
     */
    override fun addAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var someSucceeded = false

        for (element in elements) {
            someSucceeded = add(element) || someSucceeded
        }

        return someSucceeded
    }

    /**
     * Remove all elements from the set.
     */
    override fun clear() {
        countsMap.clear()
        listUpdated = false
        storedSize = 0
    }

    /**
     * Removes one occurrence of the specified element from the set, if the element exists.
     *
     * @param element [E]
     * @return [Boolean]: `true` if the element has been removed successfully, `false` otherwise
     */
    override fun remove(element: E): Boolean {
        val currentCount = getCountOf(element)

        when (currentCount) {
            0 -> return false
            1 -> countsMap.remove(element)
            else -> countsMap[element] = currentCount - 1
        }

        listUpdated = false
        storedSize--
        return true
    }

    /**
     * Remove all specified elements from the set.
     * If [elements] contains multiple occurrences of the same value, it will be removed multiple times.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: `true` if any elements have been removed successfully, `false` otherwise
     */
    override fun removeAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        var someSucceeded = false

        for (element in elements) {
            someSucceeded = remove(element) || someSucceeded
        }

        return someSucceeded
    }

    /**
     * Retain only elements that are present in [elements].
     * If [elements] contains multiple occurrences of the same value, it will retain up to that number of occurrences.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: `true` if elements have been retained successfully, `false` otherwise
     */
    override fun retainAll(elements: Collection<E>): Boolean {
        val other = MutableMultiSet(elements) // O(e)
        val keys = countsMap.keys.intersect(other.countsMap.keys) // O(max(n, e))

        val newValues = keys.map { it to min(countsMap[it]!!, other.countsMap[it]!!) } // O(n)
        clear()
        countsMap.putAll(newValues) // O(n)

        listUpdated = false
        storedSize = countsMap.values.fold(0, Int::plus) // O(n)

        return true
    }

    /**
     * Determine if an element is contained in the current MultiSet.
     *
     * @param element [E]
     * @return [Boolean]: true if [element] is in the MultiSet, false otherwise
     */
    override fun contains(element: E): Boolean = countsMap.contains(element)

    /**
     * Determine if all elements in a collection are contained in the current MultiSet.
     * If [elements] has repeats of a single element,
     * this function checks if the MultiSet has at least as many occurrence as the input collection.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if the current MultiSet contains at least as many occurrences of each value as [elements]
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val newSet = MutableMultiSet(elements)
        return newSet.countsMap.all { countsMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    /**
     * Update the values of `list` and `string` to match the current values in the set.
     */
    private fun updateList() {
        if (!listUpdated) {
            list.clear()

            countsMap.forEach {
                val element = it.key
                val count = it.value
                repeat(count) { list.add(element) }
            }

            listUpdated = true

            string = if (size == 0) {
                "[]"
            } else {
                val listString = list.joinToString(", ")
                "[$listString]"
            }
        }
    }

    /**
     * If the current set contains 0 elements.
     *
     * @return [Boolean]: `true` if the set contains 0 elements, `false` otherwise
     */
    override fun isEmpty(): Boolean = storedSize.isZero()

    /**
     * Get the number of occurrences of a given element in the current MultiSet.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    fun getCountOf(element: E): Int = countsMap[element] ?: 0

    /**
     * If two MutableMultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any?]
     * @return [Boolean]: `true` if [other] is a non-null MutableMultiSet which contains the same values, `false` otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MutableMultiSet<*>) {
            return false
        }

        val simplifiedCounts = countsMap.filterNot { it.value.isZero() }
        val otherSimplifiedCounts = other.countsMap.filterNot { it.value.isZero() }
        return simplifiedCounts == otherSimplifiedCounts
    }

    /**
     * Get an iterator for the elements in this set.
     *
     * @return [Iterator<E>]
     */
    override fun iterator(): MutableIterator<E> {
        updateList()
        return list.iterator()
    }

    /**
     * Get a string representation of the set.
     *
     * @return [String]
     */
    override fun toString(): String {
        updateList()
        return string
    }

    override fun hashCode(): Int = listOf("MutableMultiSet", countsMap).hashCode()
}
