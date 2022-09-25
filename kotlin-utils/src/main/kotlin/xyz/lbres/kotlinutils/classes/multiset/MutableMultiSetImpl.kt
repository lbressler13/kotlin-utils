package xyz.lbres.kotlinutils.classes.multiset

import xyz.lbres.kotlinutils.int.ext.isZero
import java.lang.Integer.min

/**
 * Mutable set implementation that allows multiple occurrences of the same value.
 */
internal class MutableMultiSetImpl<E> constructor(elements: Collection<E>) : MutableMultiSet<E> {
    /**
     * Number of elements in set. References a mutable variable.
     */
    override val size: Int
        get() = storedSize

    /**
     * Mutable variable to store number of elements in the set.
     */
    private var storedSize: Int = elements.size

    /**
     * Store the number of occurrences of each element in set.
     * Counts are guaranteed to be greater than zero.
     */
    private val countsMap: MutableMap<E, Int>

    /**
     * A list containing all elements in the set.
     * Values may not be up-to-date with [countsMap], and are updated only when needed to avoid frequent expensive operations.
     */
    private val list: MutableList<E>

    /**
     * Flag to indicate if the elements in the list are up-to-date with the current [countsMap].
     */
    private var listUpdated = false

    /**
     * String representation of the set.
     */
    private var string: String

    /**
     * Initialize stored variables.
     */
    init {
        countsMap = mutableMapOf()

        for (element in elements) {
            countsMap[element] = getCountOf(element) + 1
        }

        // string and list are initialized in updateList
        list = mutableListOf()
        string = ""
        updateList()
    }

    /**
     * Add one occurrence of the specified element to the set.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been added successfully, false otherwise
     */
    override fun add(element: E): Boolean {
        countsMap[element] = getCountOf(element) + 1
        storedSize++
        listUpdated = false
        return true
    }

    /**
     * Add all specified elements to the set.
     * If [elements] contains multiple occurrences of the same value, the value will be added multiple times.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if any elements have been added successfully, false otherwise
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
     * Remove one occurrence of the specified element from the set, if the element exists.
     *
     * @param element [E]
     * @return [Boolean]: true if the element has been removed successfully, false otherwise
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
     * If [elements] contains a single occurrence of a value, only one occurrence of the value will be removed from the set.
     * If there are multiple occurrences, the value will be removed multiple times.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if any elements have been removed successfully, false otherwise
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
     * If [elements] contains multiple occurrences of the same value, the value will retain up to that number of occurrences.
     *
     * @param elements [Collection<E>]
     * @return [Boolean]: true if elements have been retained successfully, false otherwise
     */
    override fun retainAll(elements: Collection<E>): Boolean {
        val other = MutableMultiSetImpl(elements)

        val elementsToRemove: MutableSet<E> = mutableSetOf()

        // update count of each element or mark as needing removal
        // cannot remove during loop, will throw a ConcurrentModificationException
        for (pair in countsMap) {
            val element = pair.key
            val currentCount = pair.value
            val newCount = min(currentCount, other.getCountOf(element))

            if (newCount.isZero()) {
                elementsToRemove.add(element)
            } else {
                countsMap[element] = newCount
            }
        }

        for (element in elementsToRemove) {
            countsMap.remove(element)
        }

        listUpdated = false
        storedSize = countsMap.values.fold(0, Int::plus)

        return true
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
     * @return [Boolean]: true if the current set contains at least as many occurrences of each value as [elements]
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) {
            return true
        }

        val newSet = MutableMultiSetImpl(elements)
        return newSet.countsMap.all { countsMap.contains(it.key) && it.value <= getCountOf(it.key) }
    }

    /**
     * Update the values of [list] and [string] to match the current values in the set.
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
     * @return [Boolean]: true if the set contains 0 elements, false otherwise
     */
    override fun isEmpty(): Boolean = storedSize.isZero()

    /**
     * Get the number of occurrences of a given element in the current set.
     *
     * @param element [E]
     * @return [Int]: the number of occurrences of [element]. 0 if the element does not exist.
     */
    override fun getCountOf(element: E): Int = countsMap.getOrDefault(element, 0)

    /**
     * If two MutableMultiSets contain the same elements, with the same number of occurrences per element.
     *
     * @param other [Any?]
     * @return [Boolean]: true if [other] is a non-null MultiSet which contains the same values as the current set, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MutableMultiSetImpl<*>) {
            return false
        }

        return countsMap == other.countsMap
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
